package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.error.InsightErrorCode;
import Team02.BackEnd.apiPayload.exception.handler.ExceptionHandler;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class InsightValidator {

    public <T> void validateInsight(final T insight) {
        if (insight == null) {
            throw new ExceptionHandler(InsightErrorCode._INSIGHT_NOT_FOUND);
        }
    }

    public <T> void validateInsightsEmpty(final List<T> insights) {
        if (insights.isEmpty()) {
            throw new ExceptionHandler(InsightErrorCode._INSIGHT_NOT_FOUND);
        }
    }
}
