package Team02.BackEnd.service.user;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCheckService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public User getUserByToken(final String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        User user = userRepository.findByEmail(email).orElse(null);
        validateUserIsNotNull(user);
        return user;
    }

    private void validateUserIsNotNull(final User user) {
        if (user == null) {
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);
        }
    }
}
