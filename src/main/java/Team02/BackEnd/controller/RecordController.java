package Team02.BackEnd.controller;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.dto.RecordRequestDto;
import Team02.BackEnd.service.FeedbackService;
import Team02.BackEnd.service.RecordService;
import Team02.BackEnd.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring/record")
public class RecordController {

    private final UserService userService;
    private final RecordService recordService;
    private final FeedbackService feedbackService;

    @PostMapping("/voice")
    @Operation(summary = "fast api -> spring", description = "첫 로그인 녹음 파일 url 저장용 api")
    public ApiResponse<Void> getVoiceUrl(@RequestHeader("Authorization") String authorizationHeader,
                                         @RequestBody RecordRequestDto.GetVoiceUrlDto getVoiceUrlDto) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        userService.updateRole(accessToken);
        recordService.getVoiceUrl(accessToken, getVoiceUrlDto);
        return ApiResponse.onSuccess(null);
    }

    @PostMapping("/respond")
    @Operation(summary = "fast api -> spring", description = "before_audio_link 미리 저장, feedback 객체 생성")
    public ApiResponse<Void> setBeforeAudioLink(@RequestBody RecordRequestDto.GetRespondDto getRespondDto) {
        feedbackService.setBeforeAudioLink(getRespondDto);
        return ApiResponse.onSuccess(null);
    }
}
