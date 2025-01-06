package Team02.BackEnd.service.user;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.userDto.UserDto;
import Team02.BackEnd.dto.userDto.UserDto.UserAnswerIndexDto;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCheckService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserByToken(final String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        User user = userRepository.findByEmail(email).orElse(null);
        validateUserIsNotNull(user);
        return user;
    }

    @Transactional(readOnly = true)
    public UserDto.UserAnswerIndexDto getUserAnswerIndexByToken(final String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        UserAnswerIndexDto userAnswerIndexDto = userRepository.findUserAnswerIndexByEmail(email).orElse(null);
        validateUserIsNotNull(userAnswerIndexDto);
        return userAnswerIndexDto;
    }

    @Transactional(readOnly = true)
    public Long getUserIdByToken(final String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        Long userId = userRepository.findUserIdByEmail(email).orElse(null);
        validateUserIsNotNull(userId);
        return userId;
    }

    @Transactional(readOnly = true)
    public UserVoiceDto getUserDataByToken(final String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        UserVoiceDto userData = userRepository.findUserDataByEmail(email).orElse(null);
        validateUserIsNotNull(userData);
        return userData;
    }

    private void validateUserIsNotNull(final Long userId) {
        if (userId == null) {
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);
        }
    }

    private <T> void validateUserIsNotNull(final T user) {
        if (user == null) {
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);
        }
    }
}
