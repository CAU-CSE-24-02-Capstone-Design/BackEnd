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
    GET_QUESTION(HttpStatus.OK, "QUESTION200", "질문 가져오기 성공"),
    SAVE_FEEDBACK(HttpStatus.OK, "FEEDBACK200", "피드백 저장 성공"),
    SAVE_STATISTICS(HttpStatus.OK, "STATISTICS200", "통계 횟수 저장 성공");

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