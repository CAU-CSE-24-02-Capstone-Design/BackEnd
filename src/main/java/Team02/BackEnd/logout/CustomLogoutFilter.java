package Team02.BackEnd.logout;

import Team02.BackEnd.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
@Slf4j
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // logout api call인지 확인한다
        String requestURI = request.getRequestURI();
        if (!requestURI.equals("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        // logout api call이면 cookie에서 refreshToken을 꺼내 삭제해야 한다.
        Optional<String> refreshToken = jwtService.extractRefreshToken(request);

        // token이 없으면
        if (refreshToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // token이 유효하지 않다면
        try {
            jwtService.isTokenValid(refreshToken.get());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // token이 블랙리스트인지 확인한다.
        if (jwtService.isBlackList(refreshToken.get())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // token이 올바르면 로그아웃을 진행한다.

        // 1. Redis에서 삭제
        jwtService.deleteRefreshToken(refreshToken.get());

        // 2. Cookie에서 RefreshToken 값을 0으로 변경
        Cookie cookie = new Cookie(jwtService.getRefreshHeader(), null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
