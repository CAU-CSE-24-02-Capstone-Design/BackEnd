package Team02.BackEnd.service.insight;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createInsight;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Insight;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class InsightManagerTest {

    @Mock
    private InsightCheckService insightCheckService;

    @Mock
    private InsightService insightService;

    @InjectMocks
    private InsightManager insightManager;

    private User user;
    private Question question;
    private Answer answer;
    private Insight insight;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        insight = createInsight(answer);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveAiInsight() {
        // given
        List<String> insights = List.of(insight.getInsight());

        // when
        insightManager.saveAiInsight(insights, answer.getId());

        // then
        verify(insightService, times(1)).saveAiInsight(insights, answer.getId());
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAiInsight() {
        // given

        // when
        insightManager.getAiInsight(answer.getId());

        // then
        verify(insightCheckService, times(1)).getAiInsight(answer.getId());
    }
}