package Team02.BackEnd.apiPayload.code.error;

import Team02.BackEnd.apiPayload.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccessTokenErrorCode implements ErrorCode {

    _ACCESSTOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "ACCESSTOKEN4001", "응답 헤더에 AccessToken이 없습니다"),
    _ACCESSTOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "ACCESSTOKEN4002", "유효하지 않은 AccessToken입니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
