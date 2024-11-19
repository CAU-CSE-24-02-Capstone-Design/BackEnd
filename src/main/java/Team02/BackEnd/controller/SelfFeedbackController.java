package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.SelfFeedbackConverter;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.dto.SelfFeedbackRequestDto;
import Team02.BackEnd.dto.SelfFeedbackResponseDto;
import Team02.BackEnd.service.SelfFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
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
public class SelfFeedbackController {

    private final SelfFeedbackService selfFeedbackService;

    @PostMapping("/self-feedbacks")
    @Operation(summary = "셀프 피드백 작성", description = "beforeAudio는 프론트에서 처리, post된 self feedback DB 저장")
    public ApiResponse<Void> saveSelfFeedback(@RequestParam("answerId") final Long answerId,
                                              @RequestBody final SelfFeedbackRequestDto.SaveSelfFeedbackDto saveSelfFeedbackDto) {
        selfFeedbackService.saveSelfFeedback(answerId, saveSelfFeedbackDto);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_SELF_FEEDBACK);
    }

    @GetMapping("/self-feedbacks/latest-feedbacks")
    @Operation(summary = "저번 녹음의 셀프 피드백 받아오기", description = "다음 질문 받기 전 메인에 띄워줄 거")
    public ApiResponse<SelfFeedbackResponseDto.getBeforeSelfFeedbackDto> getBeforeSelfFeedback(
            @RequestHeader("Authorization") final String authorizationHeader
    ) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        SelfFeedback selfFeedback = selfFeedbackService.getLatestSelfFeedback(accessToken);
        return ApiResponse.of(SuccessStatus.GET_SELF_FEEDBACK,
                SelfFeedbackConverter.toGetBeforeSelfFeedbackDto(selfFeedback));
    }

    @PostMapping("/self-feedbacks/evaluations")
    @Operation(summary = "셀프 피드백에 대한 평가 저장", description = "이전 셀프 피드백이 얼마나 잘 반영되었는지")
    public ApiResponse<Void> saveSelfFeedbackEvaluation(@RequestParam("answerId") final Long answerId,
                                                        @RequestBody final SelfFeedbackRequestDto.SaveSelfFeedbackEvaluationDto saveSelfFeedbackEvaluationDto) {
        selfFeedbackService.saveSelfFeedbackEvaluation(answerId, saveSelfFeedbackEvaluationDto.getEvaluation());
        return ApiResponse.ofNoting(SuccessStatus.SAVE_SELF_FEEDBACK_EVALUATION);
    }

    @GetMapping("/self-feedbacks/evaluations")
    @Operation(summary = "셀프 피드백에 대한 평가 저장", description = "이전 셀프 피드백이 얼마나 잘 반영되었는지")
    public ApiResponse<SelfFeedbackResponseDto.getSelfFeedbackEvaluationDto> saveSelfFeedbackEvaluation(
            @RequestParam("answerId") final Long answerId) {
        int evaluation = selfFeedbackService.getSelfFeedbackEvaluation(answerId);
        return ApiResponse.of(SuccessStatus.SAVE_SELF_FEEDBACK_EVALUATION,
                SelfFeedbackConverter.toGetSelfFeedbackEvaluationDto(evaluation));
    }
}
