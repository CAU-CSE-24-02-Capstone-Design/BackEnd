package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.dto.RecordRequestDto;
import Team02.BackEnd.service.FeedbackService;
import Team02.BackEnd.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring/records")
public class RecordController {

    private final UserService userService;
    private final FeedbackService feedbackService;

    @PostMapping("/voices")
    @Operation(summary = "fast api -> spring", description = "첫 로그인 녹음 파일 url 저장용 api")
    public ApiResponse<Void> getVoiceUrl(@RequestHeader("Authorization") final String authorizationHeader,
                                         @RequestBody final RecordRequestDto.GetVoiceUrlDto getVoiceUrlDto) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        userService.updateRoleAndVoiceUrl(accessToken, getVoiceUrlDto);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_VOICE_URL);
    }

    @PostMapping("/speeches")
    @Operation(summary = "fast api -> spring", description = "before_audio_link 미리 저장, feedback 객체 생성")
    public ApiResponse<Void> setBeforeAudioLink(@RequestHeader("Authorization") final String authorizationHeader,
                                                @RequestBody final RecordRequestDto.GetRespondDto getRespondDto) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        feedbackService.getBeforeAudioLink(accessToken, getRespondDto);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_BEFORE_AUDIO_LINK);
    }
}
