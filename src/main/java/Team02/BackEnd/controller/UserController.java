package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @DeleteMapping("/sign-out")
    public ApiResponse<Void> signOut(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        userService.signOut(accessToken);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/calenndar")
    @Operation(summary = "달력 데이터", description = "쿼리파라미터로 년,월 제공 => 기록 있는 날만 map 형태로 (날짜 : 해당 날짜 answerId) 리턴")
    public ApiResponse<HashMap<String, Long>> getDatesWhenUserDid(@RequestParam("year") String year,
                                                                  @RequestParam("month") String month) {

        return ApiResponse.of(SuccessStatus.GET_DATES_WHEN_USER_DID, userService.getDatesWhenUserDid(year, month));
    }
}
