package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.error.QuestionErrorCode;
import Team02.BackEnd.apiPayload.exception.handler.ExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class QuestionValidator {

    public <T> void validateQuestion(final T question) {
        if (question == null) {
            throw new ExceptionHandler(QuestionErrorCode._QUESTION_NOT_FOUND);
        }
    }
}
