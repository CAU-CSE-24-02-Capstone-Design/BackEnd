package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.QuestionHandler;
import org.springframework.stereotype.Component;

@Component
public class QuestionValidator {

    public <T> void validateQuestion(final T question) {
        if (question == null) {
            throw new QuestionHandler(ErrorStatus._QUESTION_NOT_FOUND);
        }
    }
}
