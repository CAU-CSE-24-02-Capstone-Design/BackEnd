package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public ApiResponse<Void> healthCheck() {
        System.out.println("제발 제발 제발 ");
        return ApiResponse.onSuccess(null);
    }
}
