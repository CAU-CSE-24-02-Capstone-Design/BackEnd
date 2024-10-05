package Team02.BackEnd.jwt.service;


import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.RefreshTokenHandler;
import Team02.BackEnd.domain.RefreshToken;
import Team02.BackEnd.exception.TokenInvalidException;
import Team02.BackEnd.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Getter
@Slf4j
public class JwtService {

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";
    private final UserRepository userRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.access.expiration}")
    private Integer accessTokenExpirationPeriod;
    @Value("${jwt.refresh.expiration}")
    private Integer refreshTokenExpirationPeriod;
    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    /**
     * AccessToken 생성 메서드
     */
    public String createAccessToken(String email) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim("unique_id", UUID.randomUUID().toString())
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .withClaim("unique_id", UUID.randomUUID().toString())
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * AccessToken을 response Header에 담기 : 발급
     */
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(accessHeader, accessToken);
        log.info("발급된 AccessToken : {}", accessToken);
    }

    /**
     * RefreshToken을 Cookie에 담기
     */
    public void sendRefreshToken(HttpServletResponse response, String refreshToken) {
        response.addCookie(createCookie(refreshHeader, refreshToken));
        log.info("발급된 RefreshToken : {}", refreshToken);
    }

    /**
     * AccessToken을 헤더에, RefreshToken을 쿠키에 담아서 보내기 - 토큰 발급
     */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        sendAccessToken(response, accessToken);
        sendRefreshToken(response, refreshToken);
        log.info("AccessToken, RefreshToken 발급 완료");
    }

    /**
     * Cookie에서 RefreshToken 추출하기
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(refreshHeader))
                refresh = cookie.getValue();
        }

        assert refresh != null;
        if (refresh.isEmpty())
            throw new RefreshTokenHandler(ErrorStatus._REFRESHTOKEN_NOT_FOUND);
        else
            return Optional.of(refresh);
    }

    /**
     * Request Header에서 AccessToken 추출하기
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    /**
     * AccessToken에서 email을 추출하기
     */
    public Optional<String> extractEmail(String accessToken) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(EMAIL_CLAIM)
                    .asString());
        } catch (Exception e) {
            log.error("AccessToken이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    /**
     * Redis에서 RefreshToken 삭제
     */
    public void deleteRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

    /**
     * Redis에 RefreshToken 저장
     */
    public void updateRefreshToken(String email, String refreshToken) {
        RefreshToken token = RefreshToken.builder()
                .email(email)
                .refresh(refreshToken)
                .expiration(refreshTokenExpirationPeriod)
                .build();

        System.out.println("Saving RefreshToken: " + token);
        redisTemplate.opsForValue().set(refreshToken, token, (long) refreshTokenExpirationPeriod, TimeUnit.MILLISECONDS);
    }

    /**
     * RefreshToken이 blacklist인지 확인한다
     */
    public boolean isBlackList(String refreshToken) {
        RefreshToken token = (RefreshToken) redisTemplate.opsForValue().get(refreshToken);
        return token == null;
    }

    /**
     * RefreshToken에서 email 가져오기
     */
    public String getEmailFromRefreshToken(String refreshToken) {
        RefreshToken token = (RefreshToken) redisTemplate.opsForValue().get(refreshToken);
        if (token != null)
            return token.getEmail();
        else
            return null;
    }

    /**
     * 토큰 유효성 검사
     */
    public void isTokenValid(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secretKey)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new TokenInvalidException("유효하지 않은 토큰");
        } catch (Exception e) {
            throw new RuntimeException("토큰 검증 중 런타임 오류 발생", e);
        }
    }

    /**
     * 쿠키 생성
     */
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(refreshTokenExpirationPeriod);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "None");
        return cookie;
    }

}
