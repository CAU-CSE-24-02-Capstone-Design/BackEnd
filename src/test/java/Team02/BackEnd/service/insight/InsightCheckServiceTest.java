package Team02.BackEnd.service.insight;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createInsight;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Insight;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.InsightRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class InsightCheckServiceTest {

    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private InsightRepository insightRepository;

    @InjectMocks
    private InsightCheckService insightCheckService;

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

    @DisplayName("AI의 인사이트를 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAiInsight() {
        // given
        List<String> insights = List.of(insight.getInsight());
        // when
//        given(answerCheckService.getAnswerByAnswerId(answer.getId())).willReturn(answer);
        given(insightRepository.findInsightsByAnswerId(answer.getId())).willReturn(insights);
//        given(insightRepository.findAllByAnswerId(answer.getId())).willReturn(insights);

        List<String> aiInsights = insightCheckService.getAiInsight(answer.getId());

        // then
        assertThat(aiInsights.size()).isEqualTo(1);
        assertThat(aiInsights.get(0)).isEqualTo(insight.getInsight());
    }
}