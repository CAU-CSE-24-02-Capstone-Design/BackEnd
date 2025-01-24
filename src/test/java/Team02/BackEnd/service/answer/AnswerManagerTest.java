package Team02.BackEnd.service.answer;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class AnswerManagerTest {

    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private AnswerService answerService;

    @InjectMocks
    private AnswerManager answerManager;

    private User user;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveAnswerEvaluation() {
        // given
        int evaluation = 2;

        // when
        answerManager.saveAnswerEvaluation(answer.getId(), evaluation);

        // then
        verify(answerService, times(1)).saveAnswerEvaluation(answer.getId(), evaluation);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnswerByAnswerId() {
        // given

        // when
        answerManager.getAnswerByAnswerId(answer.getId());

        // then
        verify(answerCheckService, times(1)).getAnswerByAnswerId(answer.getId());
    }
}