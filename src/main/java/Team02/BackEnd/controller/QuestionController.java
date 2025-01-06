package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.QuestionConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.questionDto.QuestionResponseDto;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.answer.AnswerService;
import Team02.BackEnd.service.question.QuestionCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
@Slf4j
public class QuestionController {

    private final QuestionCheckService questionCheckService;
    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final AnswerService answerService;

    @GetMapping("/questions")
    @Operation(summary = "질문 요청", description = "유저가 클릭시 공개 될 질문 가져오기")
    public ApiResponse<QuestionResponseDto.GetQuestionDto> getQuestion(
            @RequestHeader("Authorization") final String authorizationHeader,
            @RequestParam("level") final Long level) {
        User user = userCheckService.getUserByToken(
                authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT));
        Optional<Answer> latestAnswer = answerCheckService.getLatestAnswerByUserId(user.getId());
        Question question = questionCheckService.getUserQuestion(user, latestAnswer, level);
        Long answerId = answerService.createAnswer(user, question, latestAnswer, level);
        return ApiResponse.of(SuccessStatus.GET_QUESTION, QuestionConverter.toQuestionResponseDto(question, answerId));
    }
}
