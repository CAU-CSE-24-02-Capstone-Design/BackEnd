package Team02.BackEnd.validator;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public <T> void validateUser(final T user) {
        if (user == null) {
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);
        }
    }

    public <T> void validateUserList(final List<T> userList) {
        if (userList.isEmpty()) {
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);
        }
    }
}
