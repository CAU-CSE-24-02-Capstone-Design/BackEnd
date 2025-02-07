package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.error.FeedbackErrorCode;
import Team02.BackEnd.apiPayload.exception.handler.ExceptionHandler;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class FeedbackValidator {

    public <T> void validateFeedback(final T feedback) {
        if (feedback == null) {
            throw new ExceptionHandler(FeedbackErrorCode._FEEDBACK_NOT_FOUND);
        }
    }

    public <T> void validateFeedback(final List<T> feedbackList) {
        if (feedbackList.isEmpty()) {
            throw new ExceptionHandler(FeedbackErrorCode._FEEDBACK_NOT_FOUND);
        }
    }

    public <T> void validateResponseFromFastApi(final T response) {
        if (response == null) {
            throw new ExceptionHandler(FeedbackErrorCode._FAST_API_FEEDBACK_NULL);
        }
    }

    public List<String> validatePastAudioLinksIsNotIncludeToNull(final List<String> pastAudioLinks) {
        if (pastAudioLinks == null) {
            return Collections.emptyList();
        }
        return pastAudioLinks.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
