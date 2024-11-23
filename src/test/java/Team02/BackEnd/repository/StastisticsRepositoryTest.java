package Team02.BackEnd.repository;

import static org.assertj.core.api.Assertions.assertThat;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.Statistics;
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
public class StastisticsRepositoryTest {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @DisplayName("answer에 대한 통계 데이터를 가져온다")
    @Test
    void findByAnswerId() {
        // given
        User user = createUser();
        Answer answer = createAnswer(user);
        Statistics statistics = createStatistics(answer, 5L, 5.2);

        statisticsRepository.save(statistics);

        // when
        Statistics findStatistics = statisticsRepository.findByAnswerId(answer.getId());

        // then
        assertThat(findStatistics.getAnswer()).isEqualTo(answer);
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

    private Statistics createStatistics(final Answer answer, final Long gantourCount, final Double silentTime) {
        return Statistics.builder()
                .gantourCount(gantourCount)
                .silentTime(silentTime)
                .answer(answer)
                .build();
    }
}
