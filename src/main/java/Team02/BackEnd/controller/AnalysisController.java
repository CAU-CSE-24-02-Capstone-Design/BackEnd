package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.StatisticsConverter;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto.GetAnalysisDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

public class AnalysisController {

    private

    @GetMapping("/statistics/analysis")
    @Operation(summary = "7일 리포트 불러오기", description = "7일간의 데이터로 유저의 언어 습관 분석")
    public ApiResponse<GetAnalysisDto> getAnalysis(
            @RequestHeader("authorization") final String authorization) {
        String accessToken = authorization.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        String analysisText = statisticsService.getAnalysis(accessToken);
        return ApiResponse.of(SuccessStatus.GET_ANALYSIS, StatisticsConverter.toGetAnalysisDto(analysisText));
    }
}
