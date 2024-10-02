package Team02.BackEnd.apiPayload.exception.handler;

import Team02.BackEnd.apiPayload.code.BaseErrorCode;
import Team02.BackEnd.apiPayload.exception.GeneralException;

public class RefreshTokenHandler extends GeneralException {

    public RefreshTokenHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
