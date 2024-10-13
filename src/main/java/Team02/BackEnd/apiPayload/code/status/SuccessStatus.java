package Team02.BackEnd.apiPayload.code.status;


import Team02.BackEnd.apiPayload.code.BaseCode;
import Team02.BackEnd.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    _OK(HttpStatus.OK, "COMMON200", "성공"),

    //Question
    GET_QUESTION(HttpStatus.OK, "QUESTION2000", "질문 가져오기 성공"),

    //Feedback
    SAVE_FEEDBACK(HttpStatus.OK, "FEEDBACK2000", "피드백 저장 성공"),
    GET_FEEDBACK(HttpStatus.OK, "FEEDBACK2001", "피드백 가져오기 성공"),

    //Statistics
    SAVE_STATISTICS(HttpStatus.OK, "STATISTICS2000", "통계 횟수 저장 성공"),
    GET_DATES_WHEN_USER_DID(HttpStatus.OK, "USER2000", "유저가 참여한 날짜 가져오기 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
