package Team02.BackEnd.repository;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createStatistics;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.statisticsDto.StatisticsDto.StatisticsDataDto;
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

    @DisplayName("AnswerId로 통계 데이터를 가져온다")
    @Transactional
    @Test
    void findStatisticsDataDtoByAnswerId() {
        // given
        statisticsRepository.save(statistics);

        // when
        StatisticsDataDto statisticsDataDto = statisticsRepository.findStatisticsDataDtoByAnswerId(answer.getId())
                .orElse(null);

        // then
        assertThat(statisticsDataDto).isNotNull();
    }

    @DisplayName("Answer와 연결된 Statistics를 확인한다.")
    @Transactional
    @Test
    void existsByAnswerId() {
        // given
        statisticsRepository.save(statistics);

        // when
        Boolean exists = statisticsRepository.existsByAnswerId(answer.getId());

        // then
        assertThat(exists).isTrue();
    }
}
