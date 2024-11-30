package Team02.BackEnd.service.insight;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createInsight;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
class InsightServiceTest {

    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private InsightRepository insightRepository;

    @InjectMocks
    private InsightService insightService;

    private User user;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
    }

    @DisplayName("질문에 대한 AI 인사이트 생성하기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveAiInsight() {
        // given
        List<String> insights = List.of("insight");

        // when
        given(answerCheckService.getAnswerByAnswerId(answer.getId())).willReturn(answer);
        insightService.saveAiInsight(insights, answer.getId());

        // then
        verify(answerCheckService, times(1)).getAnswerByAnswerId(answer.getId());
        verify(insightRepository, times(insights.size())).save(argThat(insight ->
                insights.contains(insight.getInsight()) && insight.getAnswer().equals(answer)
        ));
    }
}