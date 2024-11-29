package Team02.BackEnd.service.reissue;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.RefreshTokenHandler;
import Team02.BackEnd.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReissueService {

    private final JwtService jwtService;

    public String getRefreshTokenInCookie(final HttpServletRequest request) {
        Optional<String> refreshToken = jwtService.extractRefreshToken(request);
        if (refreshToken.isPresent()) {
            validateRefreshToken(refreshToken.get());
            validateRefreshTokenIsBlackList(refreshToken.get());
            return refreshToken.get();
        }
        throw new RefreshTokenHandler(ErrorStatus._REFRESHTOKEN_NOT_FOUND);
    }

    public void reissueToken(final HttpServletResponse response, final String refreshToken) {
        String email = jwtService.getEmailFromRefreshToken(refreshToken);
        String newAccessToken = jwtService.createAccessToken(email);
        String newRefreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, newAccessToken, newRefreshToken);
        jwtService.deleteRefreshToken(refreshToken);
        jwtService.updateRefreshToken(email, newRefreshToken);

        log.info("사용자 토큰 재발급, email : {}", email);
    }

    private void validateRefreshToken(final String refreshToken) {
        try {
            jwtService.isTokenValid(refreshToken);
        } catch (Exception e) {
            throw new RefreshTokenHandler(ErrorStatus._REFRESHTOKEN_NOT_VALID);
        }
    }

    private void validateRefreshTokenIsBlackList(final String refreshToken) {
        if (jwtService.isBlackList(refreshToken)) {
            throw new RefreshTokenHandler(ErrorStatus._REFRESHTOKEN_BLACKLIST);
        }
    }
}
