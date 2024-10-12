package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    @Operation(summary = "일반 로그인", description = "소셜 x")
    public ResponseEntity<String> commonLogin(@RequestBody String email) {
        return ResponseEntity.ok().body(userService.getJwtToken(email));
    }
}
