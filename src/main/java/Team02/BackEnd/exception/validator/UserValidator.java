package Team02.BackEnd.exception.validator;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.domain.oauth.User;

public class UserValidator {

    public static void validateUserIsNotNull(User user) {
        if (user == null) {
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);
        }
    }
}
