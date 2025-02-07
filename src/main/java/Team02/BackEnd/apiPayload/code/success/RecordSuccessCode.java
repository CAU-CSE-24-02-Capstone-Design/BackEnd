package Team02.BackEnd.apiPayload.code.success;

import Team02.BackEnd.apiPayload.code.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RecordSuccessCode implements SuccessCode {

    SAVE_VOICE_URL(HttpStatus.OK, "RECORD2000", "첫 로그인 시 녹음 파일 저장 성공"),
    SAVE_BEFORE_AUDIO_LINK(HttpStatus.OK, "RECORD2001", "1분 스피치 녹음 파일 저장 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
