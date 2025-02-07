package Team02.BackEnd.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface SuccessCode {

    HttpStatus getHttpStatus();

    String getCode();

    String getMessage();

    String getName();
}
