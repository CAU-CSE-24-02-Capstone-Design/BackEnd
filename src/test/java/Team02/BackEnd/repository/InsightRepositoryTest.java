package Team02.BackEnd.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Insight;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InsightRepositoryTest {

    @Autowired
    private InsightRepository insightRepository;

    @DisplayName("답변 기록에 대한 AI 인사이트를 전부 가져온다.")
    @Test
    void findAllByAnswerId() {
        // given
        User user = createUser();
        Answer answer = createAnswer(user);
        Insight insight1 = createInsight(answer);
        Insight insight2 = createInsight(answer);

        insightRepository.save(insight1);
        insightRepository.save(insight2);

        // when
        List<Insight> insights = insightRepository.findAllByAnswerId(answer.getId());

        // then
        assertEquals(insights.size(), 2);
        assertThat(insights).allMatch(insight -> insight.getAnswer().equals(answer));
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

    private Insight createInsight(final Answer answer) {
        return Insight.builder()
                .insight("insight")
                .answer(answer)
                .build();
    }
}