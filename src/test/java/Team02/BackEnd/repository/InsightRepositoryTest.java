package Team02.BackEnd.repository;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createInsight;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Insight;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InsightRepositoryTest {

    @Autowired
    private InsightRepository insightRepository;

    private User user;
    private Question question;
    private Answer answer;
    private Insight insight1;
    private Insight insight2;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        insight1 = createInsight(answer);
        insight2 = createInsight(answer);
    }

    @DisplayName("답변 기록에 대한 AI 인사이트를 전부 가져온다.")
    @Transactional
    @Test
    void findAllByAnswerId() {
        // given
        insightRepository.save(insight1);
        insightRepository.save(insight2);

        // when
        List<String> insights = insightRepository.findInsightsByAnswerId(answer.getId());

        // then
        assertEquals(insights.size(), 2);
    }
}
