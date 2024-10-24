package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final JwtService jwtService;

    @PostMapping("/reissue")
    public ApiResponse<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키에서 RefreshToken 꺼내기
        Optional<String> refreshToken = jwtService.extractRefreshToken(request);

        /**
         * 유효성 검사와 블랙리스트 검사를 ExceptionAdvice로 처리할 수 없는 이유는, 이 로직이 Filter에서도 사용되기 때문.
         */

        // refreshToken 유효성 검사
        try {
            jwtService.isTokenValid(refreshToken.get());
        } catch (Exception e) {
            return ApiResponse.onFailure(ErrorStatus._REFRESHTOKEN_NOT_VALID.getCode(),
                    ErrorStatus._REFRESHTOKEN_NOT_VALID.getMessage(), null);
        }

        // 블랙리스트 검사
        if (jwtService.isBlackList(refreshToken.get())) {
            return ApiResponse.onFailure(ErrorStatus._REFRESHTOKEN_BLACKLIST.getCode(),
                    ErrorStatus._REFRESHTOKEN_BLACKLIST.getMessage(), null);
        }

        String email = jwtService.getEmailFromRefreshToken(refreshToken.get());
        String newAccessToken = jwtService.createAccessToken(email);
        String newRefreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, newAccessToken, newRefreshToken);
        jwtService.deleteRefreshToken(refreshToken.get());
        jwtService.updateRefreshToken(email, newRefreshToken);

        return ApiResponse.onSuccess(null);
    }
}
