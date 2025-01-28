package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.AnswerConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.dto.answerDto.AnswerRequestDto;
import Team02.BackEnd.dto.answerDto.AnswerResponseDto;
import Team02.BackEnd.service.answer.AnswerManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class AnswerController {

    private final AnswerManager answerManager;

    @PostMapping("/answers/evaluations")
    @Operation(summary = "스피치에 대한 평가 저장", description = "스피치가 얼마나 만족스러웠는지")
    public ApiResponse<Void> saveAnswerEvaluation(@RequestParam("answerId") final Long answerId,
                                                  @RequestBody final AnswerRequestDto.AnswerEvaluationRequestDto answerEvaluationRequestDto) {
        answerManager.saveAnswerEvaluation(answerId, answerEvaluationRequestDto.getEvaluation());
        return ApiResponse.ofNoting(SuccessStatus.SAVE_EVALUATION);
    }

    @GetMapping("/answers/evaluations")
    @Operation(summary = "스피치에 대한 평가 가져오기", description = "스피치가 얼마나 만족스러웠는지")
    public ApiResponse<AnswerResponseDto.AnswerEvaluationResponseDto> getAnswerEvaluation(
            @RequestParam("answerId") final Long answerId) {
        Answer answer = answerManager.getAnswerByAnswerId(answerId);
        return ApiResponse.of(SuccessStatus.GET_EVALUATION,
                AnswerConverter.toAnswerEvaluationResponseDto(answer.getEvaluation()));
    }
}
