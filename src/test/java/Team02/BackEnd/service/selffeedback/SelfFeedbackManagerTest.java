package Team02.BackEnd.service.selffeedback;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createFeedback;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createSelfFeedback;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.selfFeedbackDto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class SelfFeedbackManagerTest {

    @Mock
    private SelfFeedbackService selfFeedbackService;

    @Mock
    private SelfFeedbackCheckService selfFeedbackCheckService;

    @InjectMocks
    private SelfFeedbackManager selfFeedbackManager;

    private String accessToken;
    private User user;
    private Question question;
    private Answer answer;
    private Feedback feedback;
    private SelfFeedback selfFeedback;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        feedback = createFeedback(user, answer);
        selfFeedback = createSelfFeedback(answer);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveSelfFeedback() {
        // given
        SaveSelfFeedbackDto saveSelfFeedbackDto = SaveSelfFeedbackDto.builder()
                .feedback(selfFeedback.getFeedback())
                .build();

        // when
        selfFeedbackManager.saveSelfFeedback(answer.getId(), saveSelfFeedbackDto);

        // then
        verify(selfFeedbackService, times(1)).saveSelfFeedback(answer.getId(), saveSelfFeedbackDto);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getLatestSelfFeedbackText() {
        // given

        // when
        selfFeedbackManager.getLatestSelfFeedbackText(accessToken);

        // then
        verify(selfFeedbackCheckService, times(1)).getLatestSelfFeedbackText(accessToken);
    }
}