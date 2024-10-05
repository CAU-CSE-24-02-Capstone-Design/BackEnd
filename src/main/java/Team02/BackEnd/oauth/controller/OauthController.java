package Team02.BackEnd.oauth.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
import Team02.BackEnd.oauth.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")

/**
 * @Pathvariable을 통해 /oauth/kakao 등의 요청에서 kakao 부분을 oauthServerType으로 변환한다 (converter 이용)
 * 사용자가 프론트에서 /oauth/kakao를 통해 접속하면 밑 controller를 통한다.
 * oauthService를 통해 AuthCode를 받아오기 위한 URL을 생성하고 이 URL로 사용자를 Redirect시킨다.
 */

public class OauthController {

    private final OauthService oauthService;

    @SneakyThrows
    @GetMapping("/{oauthServerType}")
    ApiResponse<Void> redirectAuthCodeRequestUrl(
            @PathVariable OauthServerType oauthServerType,
            HttpServletResponse response
    ) {
        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        response.sendRedirect(redirectUrl);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/login/{oauthServerType}")
    ApiResponse<Role> login(
            @PathVariable OauthServerType oauthServerType,
            @RequestParam("code") String code,
            HttpServletResponse response
    ) {
        User user = oauthService.login(response, oauthServerType, code);
//        oauthService.saveImage(user);
        return ApiResponse.onSuccess(user.getRole());
    }
}