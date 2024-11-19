package Team02.BackEnd.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("이메일로 사용자 찾기")
    @Test
    void findByEmail() {
        // given
        User user = createUser();
        userRepository.save(user);

        // when
        Optional<User> userResult = userRepository.findByEmail("tlsgusdn4818@gmail.com");

        // then
        assertTrue(userResult.isPresent());
        assertEquals(user, userResult.get());
    }

    @DisplayName("OauthId로 사용자 찾기")
    @Test
    void findByOauthId() {
        // given
        User user = createUser();
        userRepository.save(user);

        // when
        Optional<User> userResult = userRepository.findByOauthId(new OauthId("1", OauthServerType.GOOGLE));

        // then
        assertTrue(userResult.isPresent());
        assertEquals(user, userResult.get());
    }

    private User createUser() {
        return User.builder()
                .email("tlsgusdn4818@gmail.com")
                .name("Hyun")
                .role(Role.USER)
                .oauthId(new OauthId("1", OauthServerType.GOOGLE))
                .voiceUrl("voiceUrl")
                .questionNumber(1L)
                .build();
    }
}