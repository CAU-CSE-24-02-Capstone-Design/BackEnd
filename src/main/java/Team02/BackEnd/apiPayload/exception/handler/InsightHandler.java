package Team02.BackEnd.apiPayload.exception.handler;

import Team02.BackEnd.apiPayload.code.BaseErrorCode;
import Team02.BackEnd.apiPayload.exception.GeneralException;

public class InsightHandler extends GeneralException {
    public InsightHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
