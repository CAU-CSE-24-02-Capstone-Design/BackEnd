package Team02.BackEnd.apiPayload.code.success;

import Team02.BackEnd.apiPayload.code.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AnalysisSuccessCode implements SuccessCode {

    SAVE_ANALYSIS(HttpStatus.OK, "STATISTICS2002", "유저 언어 습관 분석 생성하기 성공"),
    GET_ANALYSIS(HttpStatus.OK, "STATISTICS2003", "유저 언어 습관 분석 가져오기 성공"),
    CAN_SAVE_ANALYSIS(HttpStatus.OK, "STATISTICS2004", "유저 언어 습관 분석 생성하기 가능");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
