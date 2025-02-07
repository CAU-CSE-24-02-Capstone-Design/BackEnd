package Team02.BackEnd.apiPayload.code.error;

import Team02.BackEnd.apiPayload.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RefreshTokenErrorCode implements ErrorCode {

    _REFRESHTOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "REFRESHTOKEN4001", "쿠키에 RefreshToken이 없습니다."),
    _REFRESHTOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "REFRESHTOKEN4002", "유효하지 않은 RefreshToken입니다."),
    _REFRESHTOKEN_BLACKLIST(HttpStatus.NOT_FOUND, "REFRESHTOKEN4003", "블랙리스트인 RefreshToken입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
