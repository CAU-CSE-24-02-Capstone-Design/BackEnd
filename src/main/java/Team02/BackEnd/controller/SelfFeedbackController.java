package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.converter.SelfFeedbackConverter;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.dto.SelfFeedbackRequestDto;
import Team02.BackEnd.dto.SelfFeedbackResponseDto;
import Team02.BackEnd.service.AnswerService;
import Team02.BackEnd.service.SelfFeedbackService;
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
public class SelfFeedbackController {
    private final SelfFeedbackService selfFeedbackService;
    private final AnswerService answerService;
    @PostMapping("/self-feedback")
    @Operation(summary = "셀프 피드백 작성", description = "beforeAudio는 프론트에서 처리, post된 self feedback DB 저장")
    public ApiResponse<Void> saveSelfFeedback(@RequestParam("answerId") Long answerId,
                                              @RequestBody SelfFeedbackRequestDto.SaveSelfFeedbackDto saveSelfFeedbackDto) {

        selfFeedbackService.saveSelfFeedback(answerId, saveSelfFeedbackDto);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_SELF_FEEDBACK);
    }

    @GetMapping("/self-feedback")
    @Operation(summary = "저번 녹음의 셀프 피드백 받아오기", description = "다음 질문 받기 전 메인에 띄워줄 거")
    public ApiResponse<SelfFeedbackResponseDto.getBeforeSelfFeedbackDto> getBeforeSelfFeedback(@RequestParam("answerId") Long answerId){
        SelfFeedback selfFeedback = selfFeedbackService.getBeforeSelfFeedbackByAnswer(answerId);
        return ApiResponse.of(SuccessStatus.GET_SELF_FEEDBACK, SelfFeedbackConverter.toGetBeforeSelfFeedbackDto(selfFeedback));
    }

}
