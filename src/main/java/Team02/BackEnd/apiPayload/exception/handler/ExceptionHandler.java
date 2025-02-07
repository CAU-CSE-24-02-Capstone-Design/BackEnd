package Team02.BackEnd.apiPayload.exception.handler;

import Team02.BackEnd.apiPayload.code.ErrorCode;
import Team02.BackEnd.apiPayload.exception.GeneralException;

public class ExceptionHandler extends GeneralException {

    public ExceptionHandler(ErrorCode errorCode) {
        super(errorCode);
    }
}
