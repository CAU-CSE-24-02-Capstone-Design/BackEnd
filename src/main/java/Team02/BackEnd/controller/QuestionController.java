package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.QuestionConverter;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.dto.QuestionResponseDto;
import Team02.BackEnd.service.AnswerService;
import Team02.BackEnd.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/question")
    @Operation(summary = "질문 요청", description = "유저가 클릭시 공개 될 질문 가져오기")
    public ApiResponse<QuestionResponseDto.GetQuestionDto> getQuestion(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        Question question = questionService.getUserQuestion(accessToken);
        Long answerId = answerService.getAnswerId(accessToken, question);

        return ApiResponse.of(SuccessStatus.GET_QUESTION, QuestionConverter.toQuestionResponseDto(question, answerId));
    }
}
