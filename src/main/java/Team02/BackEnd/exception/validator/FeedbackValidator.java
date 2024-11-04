package Team02.BackEnd.exception.validator;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.FeedbackHandler;
import Team02.BackEnd.domain.Feedback;

public class FeedbackValidator {

    public static void validateFeedbackIsNotNull(Feedback feedback) {
        if (feedback == null) {
            throw new FeedbackHandler(ErrorStatus._FEEDBACK_NOT_FOUND);
        }
    }
}
