package Team02.BackEnd.apiPayload.exception.handler;

import Team02.BackEnd.apiPayload.code.BaseErrorCode;
import Team02.BackEnd.apiPayload.exception.GeneralException;

public class FeedbackHandler extends GeneralException {
    public FeedbackHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
