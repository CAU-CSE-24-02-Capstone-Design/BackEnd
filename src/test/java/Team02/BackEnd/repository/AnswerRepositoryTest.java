package Team02.BackEnd.repository;

import static org.assertj.core.api.Assertions.assertThat;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
import java.util.List;
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
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @DisplayName("사용자의 모든 답변 기록을 가져온다.")
    @Test
    void findByUserId() {
        // given
        User user = createUser();

        Answer answer1 = createAnswer(user);
        Answer answer2 = createAnswer(user);
        Answer answer3 = createAnswer(user);

        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);

        // when
        List<Answer> answers = answerRepository.findByUserId(user.getId());

        // then
        assertThat(answers).hasSize(3);
        assertThat(answers)
                .allMatch(answer -> answer.getUser().equals(user));
    }

    @DisplayName("사용자의 답변 기록 중 해당 년, 월에 속한 답변 기록을 가져온다.")
    @Test
    void findByUserAndYearAndMonth() {
        // given
        User user = createUser();

        Answer answer1 = createAnswer(user);
        Answer answer2 = createAnswer(user);
        Answer answer3 = createAnswer(user);

        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);

        // when
        List<Answer> answers = answerRepository.findByUserAndYearAndMonth(user, 2024, 11);

        // then
        assertThat(answers).hasSize(3);
        assertThat(answers)
                .allMatch(answer -> answer.getUser().equals(user));
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

    private Answer createAnswer(final User user) {
        return Answer.builder()
                .user(user)
                .question(null)
                .build();
    }
}