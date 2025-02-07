package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.error.AnalysisErrorCode;
import Team02.BackEnd.apiPayload.exception.handler.ExceptionHandler;
import Team02.BackEnd.domain.Analysis;
import org.springframework.stereotype.Component;

@Component
public class AnalysisValidator {

    public <T> void validateAnalysis(final Analysis analysis) {
        if (analysis == null) {
            throw new ExceptionHandler(AnalysisErrorCode._ANALYSIS_NOT_FOUND);
        }
    }

    public <T> void validateResponseFromFastApi(final T response) {
        if (response == null) {
            throw new ExceptionHandler(AnalysisErrorCode._FAST_API_ANALYSIS_NULL);
        }
    }
}
