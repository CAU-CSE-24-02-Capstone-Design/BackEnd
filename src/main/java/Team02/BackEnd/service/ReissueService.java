package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtService jwtService;

    public String getRefreshTokenInCookie(final HttpServletRequest request) {
        Optional<String> refreshToken = jwtService.extractRefreshToken(request);
        return refreshToken.orElse(null);
    }

    public void reissueToken(final HttpServletResponse response, final String refreshToken) {
        String email = jwtService.getEmailFromRefreshToken(refreshToken);
        String newAccessToken = jwtService.createAccessToken(email);
        String newRefreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, newAccessToken, newRefreshToken);
        jwtService.deleteRefreshToken(refreshToken);
        jwtService.updateRefreshToken(email, newRefreshToken);
    }

    public void validateRefreshToken(final String refreshToken) {
        try {
            jwtService.isTokenValid(refreshToken);
        } catch (Exception e) {
            ApiResponse.onFailure(ErrorStatus._REFRESHTOKEN_NOT_VALID.getCode(),
                    ErrorStatus._REFRESHTOKEN_NOT_VALID.getMessage(), null);
        }
    }

    public void validateRefreshTokenIsBlackList(final String refreshToken) {
        if (jwtService.isBlackList(refreshToken)) {
            ApiResponse.onFailure(ErrorStatus._REFRESHTOKEN_BLACKLIST.getCode(),
                    ErrorStatus._REFRESHTOKEN_BLACKLIST.getMessage(), null);
        }
    }
}
