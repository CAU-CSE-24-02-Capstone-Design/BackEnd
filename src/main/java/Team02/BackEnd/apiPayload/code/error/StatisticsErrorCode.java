package Team02.BackEnd.apiPayload.code.error;

import Team02.BackEnd.apiPayload.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StatisticsErrorCode implements ErrorCode {

    _STATISTICS_NOT_FOUND(HttpStatus.NOT_FOUND, "STATISTICS4001", "해당 통계가 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
