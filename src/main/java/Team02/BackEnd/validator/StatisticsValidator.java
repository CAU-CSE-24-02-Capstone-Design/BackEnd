package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.error.StatisticsErrorCode;
import Team02.BackEnd.apiPayload.exception.handler.ExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class StatisticsValidator {

    public <T> void validateStatistics(final T statistics) {
        if (statistics == null) {
            throw new ExceptionHandler(StatisticsErrorCode._STATISTICS_NOT_FOUND);
        }
    }
}
