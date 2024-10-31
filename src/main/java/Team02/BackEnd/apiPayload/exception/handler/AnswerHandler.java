package Team02.BackEnd.apiPayload.exception.handler;

import Team02.BackEnd.apiPayload.code.BaseErrorCode;
import Team02.BackEnd.apiPayload.exception.GeneralException;

public class AnswerHandler extends GeneralException {
    public AnswerHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
