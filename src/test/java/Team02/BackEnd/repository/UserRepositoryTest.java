package Team02.BackEnd.repository;

import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.userDto.UserDto.UserAnswerIndexDto;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import Team02.BackEnd.oauth.OauthServerType;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = createUser();
    }

    @DisplayName("Id로 User 찾기")
    @Transactional
    @Test
    void findById() {
        // given
        userRepository.save(user);

        // when
        User findUser = userRepository.findById(user.getId()).orElse(null);

        // then
        assertThat(findUser).isNotNull();
        assertEquals(user.getId(), findUser.getId());
    }

    @DisplayName("Email로 User 찾기")
    @Transactional
    @Test
    void findByEmail() {
        // given
        userRepository.save(user);

        // when
        Optional<User> userResult = userRepository.findByEmail("tlsgusdn4818@gmail.com");

        // then
        assertTrue(userResult.isPresent());
        assertEquals(user, userResult.get());
    }

    @DisplayName("OauthId로 User 찾기")
    @Transactional
    @Test
    void findByOauthId() {
        // given
        userRepository.save(user);

        // when
        Optional<User> userResult = userRepository.findByOauthId(new OauthId("1", OauthServerType.GOOGLE));

        // then
        assertTrue(userResult.isPresent());
        assertEquals(user, userResult.get());
    }

    @DisplayName("Email로 Role 찾기")
    @Transactional
    @Test
    void findRoleByEmail() {
        // given
        userRepository.save(user);

        // when
        Role role = userRepository.findRoleByEmail(user.getEmail()).orElse(null);

        // then
        assertThat(role).isNotNull();
        assertEquals(role, user.getRole());
    }

    @DisplayName("Email로 Id 찾기")
    @Transactional
    @Test
    void findUserIdByEmail() {
        // given
        userRepository.save(user);

        // when
        Long userId = userRepository.findUserIdByEmail(user.getEmail()).orElse(null);

        // then
        assertThat(userId).isNotNull();
        assertEquals(userId, user.getId());
    }

    @DisplayName("Email로 UserAnswerIndexDto 찾기")
    @Transactional
    @Test
    void findUserAnswerIndexDtoByEmail() {
        // given
        userRepository.save(user);

        // when
        UserAnswerIndexDto userAnswerIndexDto = userRepository.findUserAnswerIndexDtoByEmail(user.getEmail())
                .orElse(null);

        // then
        assertThat(userAnswerIndexDto).isNotNull();
        assertEquals(userAnswerIndexDto.getId(), user.getId());
    }

    @DisplayName("Email로 UserVoiceDto 찾기")
    @Transactional
    @Test
    void findUserVoiceDtoByEmail() {
        // given
        userRepository.save(user);

        // when
        UserVoiceDto userVoiceDto = userRepository.findUserVoiceDtoByEmail(user.getEmail()).orElse(null);

        // then
        assertThat(userVoiceDto).isNotNull();
        assertEquals(userVoiceDto.getId(), user.getId());
    }
}
