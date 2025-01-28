package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnswerHandler;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AnswerValidator {

    public <T> void validateAnswer(final T answer) {
        if (answer == null) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
    }

    public <T> void validateAnswersEmpty(final List<T> answers) {
        if (answers.isEmpty()) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
    }
}
