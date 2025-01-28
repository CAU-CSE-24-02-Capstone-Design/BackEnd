package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.InsightConverter;
import Team02.BackEnd.dto.insightDto.InsightRequestDto;
import Team02.BackEnd.dto.insightDto.InsightResponseDto;
import Team02.BackEnd.service.insight.InsightManager;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
@Slf4j
public class InsightController {

    private final InsightManager insightManager;

    @PostMapping("/insights")
    @Operation(summary = "인사이트 저장하기 react -> spring", description = "질문요청에서 받은 answerId로 쿼리 파라미터")
    public ApiResponse<Void> saveAiInsight(@RequestParam("answerId") final Long answerId,
                                           @RequestBody final InsightRequestDto.GetInsightDto getInsightDto) {
        insightManager.saveAiInsight(getInsightDto.getInsight(), answerId);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_INSIGHT);
    }

    @GetMapping("/insights")
    @Operation(summary = "인사이트 받아오기 react -> spring", description = "질문요청에서 받은 answerId로 쿼리 파라미터")
    public ApiResponse<InsightResponseDto.GetInsightDto> getAiInsight(@RequestParam("answerId") final Long answerId) {
        List<String> insight = insightManager.getAiInsight(answerId);
        return ApiResponse.of(SuccessStatus.GET_INSIGHT, InsightConverter.toGetInsightDto(insight));
    }
}
