package Team02.BackEnd.apiPayload.code.success;

import Team02.BackEnd.apiPayload.code.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StatisticsSuccessCode implements SuccessCode {

    SAVE_STATISTICS(HttpStatus.OK, "STATISTICS2000", "통계 횟수 저장 성공"),
    GET_DATES_WHEN_USER_DID(HttpStatus.OK, "CALENDAR2000", "유저가 참여한 날짜 가져오기 성공"),
    GET_STATISTICS(HttpStatus.OK, "STATISTICS2001", "유저 통계 데이터 가져오기 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
