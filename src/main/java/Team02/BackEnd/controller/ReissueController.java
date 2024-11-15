package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.service.ReissueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class ReissueController {

    private final ReissueService reissueService;

    @PostMapping("/reissue")
    public ApiResponse<Void> reissue(final HttpServletRequest request, final HttpServletResponse response) {

        String refreshToken = reissueService.getRefreshTokenInCookie(request);
        reissueService.validateRefreshToken(refreshToken);
        reissueService.validateRefreshTokenIsBlackList(refreshToken);
        reissueService.reissueToken(response, refreshToken);

        return ApiResponse.ofNoting(SuccessStatus.REISSUE_TOKEN);
    }
}
