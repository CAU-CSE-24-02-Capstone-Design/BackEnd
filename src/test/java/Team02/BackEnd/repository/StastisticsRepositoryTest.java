package Team02.BackEnd.repository;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createStatistics;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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

    private User user;
    private Question question;
    private Answer answer;
    private Statistics statistics;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        statistics = createStatistics(answer);
    }

    @DisplayName("answer에 대한 통계 데이터를 가져온다")
    @Transactional
    @Test
    void findByAnswerId() {
        // given
        statisticsRepository.save(statistics);

        // when
        Statistics findStatistics = statisticsRepository.findByAnswerId(answer.getId()).orElse(null);

        // then
        assertThat(findStatistics.getAnswer()).isEqualTo(answer);
    }
}
