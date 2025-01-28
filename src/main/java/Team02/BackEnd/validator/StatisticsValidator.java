package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.StatisticsHandler;
import org.springframework.stereotype.Component;

@Component
public class StatisticsValidator {

    public <T> void validateStatistics(final T statistics) {
        if (statistics == null) {
            throw new StatisticsHandler(ErrorStatus._STATISTICS_NOT_FOUND);
        }
    }
}
