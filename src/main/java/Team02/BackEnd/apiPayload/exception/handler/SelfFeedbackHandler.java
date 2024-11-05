package Team02.BackEnd.apiPayload.exception.handler;

import Team02.BackEnd.apiPayload.code.BaseErrorCode;
import Team02.BackEnd.apiPayload.exception.GeneralException;

public class SelfFeedbackHandler extends GeneralException {
    public SelfFeedbackHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
