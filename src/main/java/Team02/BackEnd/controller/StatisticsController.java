package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.dto.FeedbackRequestDto;
import Team02.BackEnd.dto.StatisticsRequestDto;
import Team02.BackEnd.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private StatisticsService statisticsService;

    @PostMapping("/statistics")
    @Operation(summary = "fast api -> spring", description = "통계(간투어 등 횟수) 디비 저장용 api")
    public ApiResponse<Void> saveStatistics(@RequestBody StatisticsRequestDto.GetStatisticsDto request){
        statisticsService.saveStatistics(request);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_STATISTICS);
    }
}
