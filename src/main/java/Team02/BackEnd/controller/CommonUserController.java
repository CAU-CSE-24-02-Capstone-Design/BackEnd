package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.dto.user.CommonUserRequest;
import Team02.BackEnd.dto.user.CommonUserResponse;
import Team02.BackEnd.service.CommonUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommonUserController {

    private final CommonUserService commonUserService;

    @PostMapping("/sign-up")
    public ApiResponse<CommonUserResponse.CommonUserIdResponseDTO> signUp(@RequestBody CommonUserRequest.CommonUserSignUpRequestDTO commonUserSignUpRequestDTO) {
        CommonUserResponse.CommonUserIdResponseDTO responseDTO = commonUserService.signUp(commonUserSignUpRequestDTO);
        return ApiResponse.onSuccess(responseDTO);
    }

    @DeleteMapping("/sign-out")
    public ApiResponse<Void> signOut(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        commonUserService.signOut(accessToken);
        return ApiResponse.onSuccess(null);
    }
}
