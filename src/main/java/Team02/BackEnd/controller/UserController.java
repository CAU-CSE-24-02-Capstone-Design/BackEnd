package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.success.CommonSuccessCode;
import Team02.BackEnd.apiPayload.code.success.UserSuccessCode;
import Team02.BackEnd.converter.UserConverter;
import Team02.BackEnd.dto.userDto.UserResponseDto;
import Team02.BackEnd.service.user.UserManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
@Slf4j
public class UserController {

    private final UserManager userManager;

    @PostMapping("/sign-up")
    public ApiResponse<UserResponseDto.UserDto> signUp(final HttpServletResponse response) {
        String accessToken = userManager.signUp(response);
        return ApiResponse.of(CommonSuccessCode._OK, UserConverter.toUserDto(accessToken));
    }

    @DeleteMapping("/sign-out")
    public ApiResponse<Void> signOut(@RequestHeader("Authorization") final String authorizationHeader) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        userManager.signOut(accessToken);
        return ApiResponse.ofNoting(UserSuccessCode.SIGN_OUT_USER);
    }
}
