package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.error.AnswerErrorCode;
import Team02.BackEnd.apiPayload.exception.handler.ExceptionHandler;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AnswerValidator {

    public <T> void validateAnswer(final T answer) {
        if (answer == null) {
            throw new ExceptionHandler(AnswerErrorCode._ANSWER_NOT_FOUND);
        }
    }

    public <T> void validateAnswersEmpty(final List<T> answers) {
        if (answers.isEmpty()) {
            throw new ExceptionHandler(AnswerErrorCode._ANSWER_NOT_FOUND);
        }
    }
}
