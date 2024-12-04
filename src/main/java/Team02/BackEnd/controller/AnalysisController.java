package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.AnalysisConverter;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto;
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

    private final AnalysisService analysisService;
    private final AnalysisCheckService analysisCheckService;

    @GetMapping("/analysis/available")
    @Operation(summary = "7일 리포트 생성 가능한 지 확인하기", description = "데이터 7개 쌓였는지 확인")
    public ApiResponse<AnalysisResponseDto.GetAvailabilityAnalysisDto> canSaveAnalysis(
            @RequestHeader("authorization") final String authorizationHeader) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        boolean canSaveAnalysis = analysisCheckService.canSaveAnalysis(accessToken);
        return ApiResponse.of(SuccessStatus.CAN_SAVE_ANALYSIS,
                AnalysisConverter.toGetAvailabilityAnalysisDto(canSaveAnalysis));
    }

    @PostMapping("/analysis")
    @Operation(summary = "7일 리포트 생성하기", description = "7일간의 데이터로 유저의 언어 습관 분석")
    public ApiResponse<Void> saveAnalysis(
            @RequestHeader("authorization") final String authorizationHeader) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        analysisService.saveAnalysis(accessToken);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_ANALYSIS);
    }

    @GetMapping("/analysis")
    @Operation(summary = "7일 리포트 불러오기", description = "")
    public ApiResponse<AnalysisResponseDto.GetAnalysisDto> getAnalysis(
            @RequestHeader("authorization") final String authorizationHeader) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        return ApiResponse.of(SuccessStatus.GET_ANALYSIS, analysisCheckService.getAnalysis(accessToken));
    }
}
