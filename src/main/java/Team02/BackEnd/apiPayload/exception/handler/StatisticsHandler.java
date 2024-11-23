package Team02.BackEnd.apiPayload.exception.handler;

import Team02.BackEnd.apiPayload.code.BaseErrorCode;
import Team02.BackEnd.apiPayload.exception.GeneralException;

public class StatisticsHandler extends GeneralException {
    public StatisticsHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
