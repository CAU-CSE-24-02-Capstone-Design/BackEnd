package Team02.BackEnd.apiPayload.code.success;

import Team02.BackEnd.apiPayload.code.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SelfFeedbackSuccessCode implements SuccessCode {

    SAVE_SELF_FEEDBACK(HttpStatus.CREATED, "SELFFEEDBACK2000", "셀프 피드백 저장 성공"),
    GET_SELF_FEEDBACK(HttpStatus.OK, "SELFFEEDBACK2001", "셀프 피드백 가져오기 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
