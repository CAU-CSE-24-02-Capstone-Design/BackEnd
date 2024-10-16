package Team02.BackEnd.apiPayload.exception.handler;

import Team02.BackEnd.apiPayload.code.BaseErrorCode;
import Team02.BackEnd.apiPayload.exception.GeneralException;

public class FamilyHandler extends GeneralException {

    public FamilyHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
