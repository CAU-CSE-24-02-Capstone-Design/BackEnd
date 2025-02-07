package Team02.BackEnd.apiPayload.code.error;

import Team02.BackEnd.apiPayload.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SelfFeedbackErrorCode implements ErrorCode {

    _SELF_FEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND, "SELFFEEDBACK4001", "해당 셀프 피드백이 없습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
