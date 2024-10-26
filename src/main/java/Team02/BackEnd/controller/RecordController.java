package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.dto.RecordRequestDto;
import Team02.BackEnd.service.FeedbackService;
import Team02.BackEnd.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring/record")
public class RecordController {

    private final RecordService recordService;
    private final FeedbackService feedbackService;

    @PostMapping("/voice")
    @Operation(summary = "fast api -> spring", description = "첫 로그인 녹음 파일 url 저장용 api")
    public ApiResponse<Void> getVoiceUrl(@RequestHeader("Authorization") String authorizationHeader,
                                         @RequestBody RecordRequestDto.GetVoiceUrlDto getVoiceUrlDto) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        recordService.getVoiceUrl(accessToken, getVoiceUrlDto);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/respond")
    @Operation(summary = "fast api -> spring", description = "질문에 대한 유저의 답변 url 저장용 api")
    public ApiResponse<Void> getAnswer(@RequestHeader("Authorization") String authorizationHeader,
                                       @RequestBody RecordRequestDto.GetRespondDto getRespondDto) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        feedbackService.setBeforeAudioLink(getRespondDto);
        return ApiResponse.onSuccess(null);
    }
}
