package Team02.BackEnd.apiPayload.exception.handler;

import Team02.BackEnd.apiPayload.code.BaseErrorCode;
import Team02.BackEnd.apiPayload.exception.GeneralException;

public class AnalysisHandler extends GeneralException {
    public AnalysisHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
