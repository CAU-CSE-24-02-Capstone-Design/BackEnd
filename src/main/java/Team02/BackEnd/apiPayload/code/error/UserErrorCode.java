package Team02.BackEnd.apiPayload.code.error;

import Team02.BackEnd.apiPayload.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    _USER_DUPLICATED(HttpStatus.BAD_REQUEST, "USER4001", "중복 이메일입니다."),
    _USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4002", "해당 유저가 없습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName(){
        return this.name();
    }
}
