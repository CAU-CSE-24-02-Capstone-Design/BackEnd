package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.dto.FeedbackResponseDto;
import Team02.BackEnd.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/feedbacks")
    @Operation(summary = "피드백 생성하기 react -> spring", description = "질문요청에서 받은 answerId로 쿼리 파라미터")
    public ApiResponse<Void> getFeedback(
            @RequestHeader("Authorization") final String authorizationHeader,
            @RequestParam("answerId") final Long answerId) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        feedbackService.createFeedbackData(accessToken, answerId);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_FEEDBACK);
    }

    @GetMapping("/feedbacks")
    @Operation(summary = "피드백 데이터 요청하기 react -> spring", description = "질문요청에서 받은 answerId로 쿼리 파라미터")
    public ApiResponse<FeedbackResponseDto.GetFeedbackDto> geFeedback(@RequestParam("answerId") final Long answerId) {
        Feedback feedback = feedbackService.getFeedbackByAnswerId(answerId);
        return ApiResponse.of(SuccessStatus.GET_FEEDBACK, FeedbackConverter.toGetFeedbackDto(feedback));
    }
}
