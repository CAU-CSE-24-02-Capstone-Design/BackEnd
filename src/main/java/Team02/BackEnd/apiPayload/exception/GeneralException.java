package Team02.BackEnd.apiPayload.exception;

import Team02.BackEnd.apiPayload.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private ErrorCode code;
}