package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.AnswerConverter;
import Team02.BackEnd.dto.answerDto.AnswerRequestDto;
import Team02.BackEnd.dto.answerDto.AnswerResponseDto;
import Team02.BackEnd.service.AnswerService;
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
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/answers/completions")
    @Operation(summary = "오늘 답변 했는지 여부 받아오기 react -> spring", description = "오늘 답변 했는지 여부 받아오기")
    public ApiResponse<AnswerResponseDto.AnswerExistsDto> doAnswerToday(
            @RequestHeader("Authorization") final String authorizationHeader) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        Boolean isAnswerExists = answerService.doAnswerToday(accessToken);
        return ApiResponse.of(SuccessStatus.CHECK_TODAY_ANSWER_EXISTS,
                AnswerConverter.toAnswerExistsDto(isAnswerExists));
    }

    @PostMapping("/answers/evaluations")
    @Operation(summary = "스피치에 대한 평가 저장", description = "스피치가 얼마나 만족스러웠는지")
    public ApiResponse<Void> saveAnswerEvaluation(@RequestParam("answerId") final Long answerId,
                                                  @RequestBody final AnswerRequestDto.AnswerEvaluationRequestDto answerEvaluationRequestDto) {
        answerService.saveAnswerEvaluation(answerId, answerEvaluationRequestDto.getEvaluation());
        return ApiResponse.ofNoting(SuccessStatus.SAVE_EVALUATION);
    }

    @GetMapping("/answers/evaluations")
    @Operation(summary = "스피치에 대한 평가 가져오기", description = "스피치가 얼마나 만족스러웠는지")
    public ApiResponse<AnswerResponseDto.AnswerEvaluationResponseDto> getAnswerEvaluation(
            @RequestParam("answerId") final Long answerId) {
        int evaluation = answerService.getAnswerEvaluation(answerId);
        return ApiResponse.of(SuccessStatus.GET_EVALUATION,
                AnswerConverter.toAnswerEvaluationResponseDto(evaluation));
    }
}
