package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto;
import Team02.BackEnd.service.feedback.FeedbackManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
@Slf4j
public class FeedbackController {

    private final FeedbackManager feedbackManager;

    @PostMapping("/feedbacks")
    @Operation(summary = "피드백 생성하기 react -> spring", description = "질문요청에서 받은 answerId로 쿼리 파라미터")
    public ApiResponse<Void> createFeedback(
            @RequestHeader("Authorization") final String authorizationHeader,
            @RequestParam("answerId") final Long answerId) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        feedbackManager.createFeedbackData(accessToken, answerId);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_FEEDBACK);
    }

    @GetMapping("/feedbacks")
    @Operation(summary = "피드백 데이터 요청하기 react -> spring", description = "질문요청에서 받은 answerId로 쿼리 파라미터")
    public ApiResponse<FeedbackResponseDto.GetFeedbackDto> getFeedback(@RequestParam("answerId") final Long answerId) {
        Feedback feedback = feedbackManager.getFeedbackByAnswerId(answerId);
        return ApiResponse.of(SuccessStatus.GET_FEEDBACK, FeedbackConverter.toGetFeedbackDto(feedback));
    }

    @GetMapping("/feedbacks/completions")
    @Operation(summary = "오늘 답변 했는지 여부 받아오기 react -> spring", description = "오늘 답변 했는지 여부 받아오기")
    public ApiResponse<FeedbackResponseDto.SpeechExistsDto> doSpeechToday(
            @RequestHeader("Authorization") final String authorizationHeader) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        Boolean isSpeechExists = feedbackManager.doSpeechToday(accessToken);
        return ApiResponse.of(SuccessStatus.CHECK_TODAY_ANSWER_EXISTS,
                FeedbackConverter.toGetSpeechExistsDto(isSpeechExists));
    }
}
