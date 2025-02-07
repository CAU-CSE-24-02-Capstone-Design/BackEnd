package Team02.BackEnd.apiPayload.code.success;

import Team02.BackEnd.apiPayload.code.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserSuccessCode implements SuccessCode {

    OAUTH_REDIRECT(HttpStatus.OK, "OAUTH2000", "소셜 로그인 리다이렉트 성공"),
    OAUTH_LOGIN(HttpStatus.OK, "OAUTH2001", "소셜 로그인 성공"),
    SIGN_OUT_USER(HttpStatus.OK, "USER2000", "회원 탈퇴 성공"),
    SUCCESS_UPDATE_SCORE(HttpStatus.OK, "USER2001", "출석 점수 업데이트 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
