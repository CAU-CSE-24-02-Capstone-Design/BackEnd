package Team02.BackEnd.apiPayload.exception.handler;

import Team02.BackEnd.apiPayload.code.BaseErrorCode;
import Team02.BackEnd.apiPayload.exception.GeneralException;

public class AccessTokenHandler extends GeneralException {

    public AccessTokenHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
