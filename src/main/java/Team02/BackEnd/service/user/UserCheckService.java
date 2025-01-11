package Team02.BackEnd.service.user;

import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.userDto.UserDto;
import Team02.BackEnd.dto.userDto.UserDto.UserAnswerIndexDto;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import Team02.BackEnd.dto.userDto.UserPrincipal;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.UserRepository;
import Team02.BackEnd.validator.UserValidator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public class UserCheckService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Cacheable(value = "user", key = "#p0", cacheManager = "cacheManager")
    public Optional<UserPrincipal> getUserPrincipalByEmail(final String email) {
        Role role = userRepository.findRoleByEmail(email).orElse(null);
        userValidator.validateUser(role);
        return Optional.of(UserPrincipal.builder()
                .email(email)
                .role(role)
                .build());
    }

    public User getUserByToken(final String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        User user = userRepository.findByEmail(email).orElse(null);
        userValidator.validateUser(user);
        return user;
    }

    public UserDto.UserAnswerIndexDto getUserAnswerIndexByToken(final String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        UserAnswerIndexDto userAnswerIndexDto = userRepository.findUserAnswerIndexByEmail(email).orElse(null);
        userValidator.validateUser(userAnswerIndexDto);
        return userAnswerIndexDto;
    }

    public Long getUserIdByToken(final String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        Long userId = userRepository.findUserIdByEmail(email).orElse(null);
        userValidator.validateUser(userId);
        return userId;
    }

    public UserVoiceDto getUserDataByToken(final String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        UserVoiceDto userData = userRepository.findUserDataByEmail(email).orElse(null);
        userValidator.validateUser(userData);
        return userData;
    }
}
