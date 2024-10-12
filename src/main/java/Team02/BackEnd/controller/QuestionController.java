package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.QuestionConverter;
import Team02.BackEnd.dto.QuestionResponseDto;
import Team02.BackEnd.service.AnswerService;
import Team02.BackEnd.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/question")
    @Operation(summary = "질문 요청", description = "유저가 클릭시 공개 될 질문 가져오기")
    public ApiResponse<QuestionResponseDto.GetQuestionDto> getQuestion(){

        String questionDescription = questionService.getQuestionDescription();
        Long answerId = answerService.getAnswerId();

        return ApiResponse.of(SuccessStatus.GET_QUESTION, QuestionConverter.toQuestionResponseDto(questionDescription, answerId));
    }
}
