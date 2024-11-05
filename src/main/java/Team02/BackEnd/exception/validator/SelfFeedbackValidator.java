package Team02.BackEnd.exception.validator;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.SelfFeedbackHandler;
import Team02.BackEnd.domain.SelfFeedback;

public class SelfFeedbackValidator {
    public static void validateSelfFeedbackIsNotNull(SelfFeedback selfFeedback) {
        if (selfFeedback == null) {
            throw new SelfFeedbackHandler(ErrorStatus._SELF_FEEDBACK_NOT_FOUND);
        }
    }
}
