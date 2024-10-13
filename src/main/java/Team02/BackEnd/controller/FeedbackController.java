package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.dto.FeedbackRequestDto;
import Team02.BackEnd.dto.FeedbackResponseDto;
import Team02.BackEnd.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedbackController {

    private FeedbackService feedbackService;

    @PostMapping("/feedback")
    @Operation(summary = "fast api -> spring", description = "유저 피드백 디비 저장용 api")
    public ApiResponse<Void> saveFeedback(@RequestBody FeedbackRequestDto.GetFeedbackDto request){
        feedbackService.saveFeedback(request);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_FEEDBACK);
    }

    @GetMapping("/feedback")
    @Operation(summary = "피드백 받아오기 react -> spring", description = "질문요청에서 받은 answerId로 쿼리 파라미터")
    public ApiResponse<FeedbackResponseDto.GetFeedbackDto> getFeedback(@RequestParam("answerId") Long answerId){
        Feedback feedback = feedbackService.getFeedback(answerId);
        return ApiResponse.of(SuccessStatus.GET_FEEDBACK, FeedbackConverter.toGetFeedbackDto(feedback));
    }

}
