package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.dto.user.UserRequest;
import Team02.BackEnd.service.FamilyService;
import Team02.BackEnd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;
    private final UserService userService;

    @PostMapping("/family")
    public ApiResponse<?> connect(@RequestHeader("Authorization") String requestHeader,
                                  @RequestBody UserRequest.UserFamilyInfoRequestDTO userFamilyInfoRequestDTO) {
        String accessToken = requestHeader.replace("Bearer ", "");
        userService.updateFamilyInfo(accessToken, userFamilyInfoRequestDTO);
        familyService.connect(accessToken);
        return ApiResponse.onSuccess(null);
    }
}
