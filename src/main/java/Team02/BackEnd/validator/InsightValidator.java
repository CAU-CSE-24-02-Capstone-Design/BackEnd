package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.InsightHandler;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class InsightValidator {

    public <T> void validateInsight(final T insight) {
        if (insight == null) {
            throw new InsightHandler(ErrorStatus._INSIGHT_NOT_FOUND);
        }
    }

    public <T> void validateInsightsEmpty(final List<T> insights) {
        if (insights.isEmpty()) {
            throw new InsightHandler(ErrorStatus._INSIGHT_NOT_FOUND);
        }
    }
}
