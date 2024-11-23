package Team02.BackEnd.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
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
class SelfFeedbackRepositoryTest {

    @Autowired
    private SelfFeedbackRepository selfFeedbackRepository;

    @DisplayName("답변 기록에 대한 셀프 피드백 찾아오기")
    @Test
    void findByAnswerId() {
        // given
        User user = createUser();
        Answer answer = createAnswer(user);
        SelfFeedback selfFeedback = createSelfFeedback(answer);

        selfFeedbackRepository.save(selfFeedback);

        // when
        SelfFeedback selfFeedbackResult = selfFeedbackRepository.findByAnswerId(answer.getId());

        // then
        assertEquals(selfFeedback, selfFeedbackResult);
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
                .evaluation(0)
                .build();
    }

    private SelfFeedback createSelfFeedback(final Answer answer) {
        return SelfFeedback.builder()
                .feedback("feedback")
                .answer(answer)
                .build();
    }
}