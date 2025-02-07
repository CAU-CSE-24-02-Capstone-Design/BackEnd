package Team02.BackEnd.apiPayload.code.success;

import Team02.BackEnd.apiPayload.code.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InsightSuccessCode implements SuccessCode {

    SAVE_INSIGHT(HttpStatus.OK, "INSIGHT2000", "인사이트 저장 성공"),
    GET_INSIGHT(HttpStatus.OK, "INSIGHT2001", "인사이트 가져오기 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
