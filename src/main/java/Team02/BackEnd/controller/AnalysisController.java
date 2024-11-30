package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.AnalysisConverter;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto;
import Team02.BackEnd.service.analysis.AnalysisApiService;
import Team02.BackEnd.service.analysis.AnalysisCheckService;
import Team02.BackEnd.service.analysis.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class AnalysisController {

    private final AnalysisApiService analysisApiService;
    private final AnalysisCheckService analysisCheckService;
    private final AnalysisService analysisService;

    @PostMapping("/analysis")
    @Operation(summary = "7일 리포트 생성하기", description = "7일간의 데이터로 유저의 언어 습관 분석")
    public ApiResponse<Void> createAnalysis(
            @RequestHeader("authorization") final String authorization) {
        String accessToken = authorization.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        analysisApiService.createAnalysis(accessToken);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_ANALYSIS);
    }

    @GetMapping("/analysis")
    @Operation(summary = "7일 리포트 불러오기", description = "생성 후 받은 analysis_id로 콜")
    public ApiResponse<AnalysisResponseDto.GetAnalysisDto> getAnalysis(
            @RequestHeader("authorization") final String authorization) {
        String accessToken = authorization.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        String analysisText = analysisCheckService.getAnalysis(accessToken);
        return ApiResponse.of(SuccessStatus.GET_ANALYSIS, AnalysisConverter.toGetAnalysisDto(analysisText));
    }
}
