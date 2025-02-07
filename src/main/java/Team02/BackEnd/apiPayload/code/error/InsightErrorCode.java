package Team02.BackEnd.apiPayload.code.error;

import Team02.BackEnd.apiPayload.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InsightErrorCode implements ErrorCode {

    _INSIGHT_INVALID_CONVERT(HttpStatus.INTERNAL_SERVER_ERROR, "INSIGHT5001", "Insight 변환에 실패했습니다."),
    _INSIGHT_NOT_FOUND(HttpStatus.NOT_FOUND, "INSIGHT5002", "Insight가 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
