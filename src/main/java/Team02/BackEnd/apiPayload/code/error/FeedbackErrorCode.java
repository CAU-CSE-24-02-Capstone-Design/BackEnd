package Team02.BackEnd.apiPayload.code.error;

import Team02.BackEnd.apiPayload.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FeedbackErrorCode implements ErrorCode {

    _FEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND, "FEEDBACK4001", "해당 피드백이 없습니다"),
    _FAST_API_FEEDBACK_NULL(HttpStatus.BAD_REQUEST, "FEEDBACK4002", "Fast api return null");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
