package Team02.BackEnd.jwt.filter;

import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.exception.TokenInvalidException;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.jwt.util.PasswordUtil;
import Team02.BackEnd.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * Jwt 인증 필터 로그인 이외의 URI 요청이 왔을 때 처리하는 필터
 * <p>
 * AccessToken 유효성 검사를 진행하고 유효하지 않으면 프론트로 에러 메시지를 보낸다. 프론트에서 에러 메시지를 받으면 재발급 API CALL
 */

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println(path);
        if (isSwaggerPath(request) || path.startsWith("/login") || path.startsWith("/reissue") || path.startsWith(
                "/oauth") || path.startsWith("/sign-up") || path.startsWith("/google-login") || path.startsWith("/health")){
            log.debug("JWT Authentication Filter Skip");
            filterChain.doFilter(request, response);
            return;
        }

        // AccessToken 체크 및 인증 처리
        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    private boolean isSwaggerPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/swagger") ||
                uri.startsWith("/v3/api-docs") ||
                uri.startsWith("/api-docs") ||
                uri.startsWith("/swagger-ui.html") ||
                uri.startsWith("/swagger-ui") ||
                uri.startsWith("/webjars") ||
                uri.startsWith("/favicon.ico") ||
                uri.startsWith("/csrf") ||
                uri.startsWith("/v3/api-docs.yaml") ||
                uri.startsWith("/v3/api-docs.json") ||
                uri.startsWith("/swagger-resources") ||
                uri.startsWith("/swagger-resources/configuration/ui") ||
                uri.startsWith("/swagger-resources/configuration/security");
    }

    /**
     * [AccessToken 체크 & 인증 처리 메소드] request에서 extractAccessToken()으로 AccessToken 추출 후, isTokenValid()로 유효한 토큰인지 검증 유효한
     * 토큰이면, AccessToken서 extractEmail로 Email을 추출한 후 findByEmail()로 해당 이메일을 사용하는 유저 객체 반환 그 유저 객체를
     * saveAuthentication()으로 인증 처리하여 인증 허가 처리된 객체를 SecurityContextHolder에 담기 그 후 다음 인증 필터로 진행
     */
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws IOException, ServletException {
        log.info("checkAccessTokenAndAuthentication() 호출");

        // 스웨거 페이지 토큰 해제
        if (isSwaggerPath(request)) {
            log.info("Swagger 토큰 미필요");
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtService.extractAccessToken(request).orElse(null);
        if (accessToken == null) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "토큰이 없습니다");
            return;
        }

        try {
            jwtService.isTokenValid(accessToken);
            jwtService.extractEmail(accessToken)
                    .ifPresent(email -> userRepository.findByEmail(email)
                            .ifPresent(this::saveAuthentication));
        } catch (TokenInvalidException e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        } catch (RuntimeException e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "토큰 오류 : " + e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

    /**
     * [인증 허가 메소드] 파라미터의 유저 : 우리가 만든 회원 객체 / 빌더의 유저 : UserDetails의 User 객체
     * <p>
     * new UsernamePasswordAuthenticationToken()로 인증 객체인 Authentication 객체 생성 UsernamePasswordAuthenticationToken의 파라미터
     * 1. 위에서 만든 UserDetailsUser 객체 (유저 정보) 2. credential(보통 비밀번호로, 인증 시에는 보통 null로 제거) 3. Collection < ? extends
     * GrantedAuthority>로, UserDetails의 User 객체 안에 Set<GrantedAuthority> authorities이 있어서 getter로 호출한 후에, new
     * NullAuthoritiesMapper()로 GrantedAuthoritiesMapper 객체를 생성하고 mapAuthorities()에 담기
     * <p>
     * SecurityContextHolder.getContext()로 SecurityContext를 꺼낸 후, setAuthentication()을 이용하여 위에서 만든 Authentication 객체에 대한
     * 인증 허가 처리
     */
    public void saveAuthentication(User user) {

        // OAuth2 유저는 password가 없기 때문에 랜덤 생성
        String password = null;
        password = PasswordUtil.generateRandomPassword();

        assert password != null;
        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(password)
                .roles(user.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // 오류 정보 {status, message}를 JSON 형태로 바꿔서 응답
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
        mapper.writeValue(out, errorResponse);
    }

    public static class ErrorResponse {
        private int status;
        private String message;

        // Constructors, getters, and setters
        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
