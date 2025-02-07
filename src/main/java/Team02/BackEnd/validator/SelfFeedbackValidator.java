package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.error.SelfFeedbackErrorCode;
import Team02.BackEnd.apiPayload.exception.handler.ExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class SelfFeedbackValidator {

    public <T> void validateSelfFeedback(final T selfFeedback) {
        if (selfFeedback == null) {
            throw new ExceptionHandler(SelfFeedbackErrorCode._SELF_FEEDBACK_NOT_FOUND);
        }
    }
}
