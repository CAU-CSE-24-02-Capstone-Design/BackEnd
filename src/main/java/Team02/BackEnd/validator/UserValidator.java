package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.error.UserErrorCode;
import Team02.BackEnd.apiPayload.exception.handler.ExceptionHandler;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public <T> void validateUser(final T user) {
        if (user == null) {
            throw new ExceptionHandler(UserErrorCode._USER_NOT_FOUND);
        }
    }

    public <T> void validateUserList(final List<T> userList) {
        if (userList.isEmpty()) {
            throw new ExceptionHandler(UserErrorCode._USER_NOT_FOUND);
        }
    }
}
