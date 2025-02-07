package Team02.BackEnd.apiPayload.code.success;

import Team02.BackEnd.apiPayload.code.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AnswerSuccessCode implements SuccessCode {

    CHECK_TODAY_ANSWER_EXISTS(HttpStatus.OK, "ANSWER2000", "오늘 답변 했는 지 여부 가져오기 성공"),
    SAVE_EVALUATION(HttpStatus.OK, "ANSWER2001", "스스로 평가 저장 성공"),
    GET_EVALUATION(HttpStatus.OK, "ANSWER2002", "스스로 평가 가져오기 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
