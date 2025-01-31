package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.dto.statisticsDto.StatisticsRequestDto;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto;
import Team02.BackEnd.service.statistics.StatisticsManager;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class StatisticsController {

    private final StatisticsManager statisticsManager;

    @PostMapping("/statistics")
    @Operation(summary = "fast api -> spring", description = "통계(간투어 등 횟수) 디비 저장용 api, answerId 같이 넘겨주기")
    public ApiResponse<Void> saveStatistics(@RequestBody final StatisticsRequestDto.GetStatisticsDto getStatisticsDto) {
        statisticsManager.saveStatistics(getStatisticsDto);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_STATISTICS);
    }

    @GetMapping("/statistics")
    @Operation(
            summary = "통계 데이터 불러오기 react -> spring",
            description = "모든 스피치 내역 가져오기"
    )
    public ApiResponse<List<StatisticsResponseDto.GetStatisticsDto>> getFilterStatistics(
            @RequestHeader("Authorization") final String authorizationHeader) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        return ApiResponse.of(SuccessStatus.GET_STATISTICS, statisticsManager.getUserStatistics(accessToken));
    }

    @GetMapping("/statistics/levels")
    @Operation(summary = "난이도 별 통계 데이터 불러오기 react -> spring", description = "통계 데이터 가져오기")
    public ApiResponse<List<StatisticsResponseDto.GetStatisticsDto>> getFilterStatistics(
            @RequestHeader("Authorization") final String authorizationHeader,
            @RequestParam("level") final Long level
    ) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        return ApiResponse.of(SuccessStatus.GET_STATISTICS,
                statisticsManager.getUserStatisticsByLevel(accessToken, level));
    }
}
