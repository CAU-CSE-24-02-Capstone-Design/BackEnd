package Team02.BackEnd.apiPayload.exception.handler;

import Team02.BackEnd.apiPayload.code.BaseErrorCode;
import Team02.BackEnd.apiPayload.exception.GeneralException;

public class QuestionHandler extends GeneralException {

    public QuestionHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
