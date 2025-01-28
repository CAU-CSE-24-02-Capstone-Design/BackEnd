package Team02.BackEnd.service.selffeedback;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createSelfFeedback;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.selfFeedbackDto.SelfFeedbackRequestDto;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class SelfFeedbackServiceTest {

    @Mock
    private SelfFeedbackCheckService selfFeedbackCheckService;
    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private SelfFeedbackRepository selfFeedbackRepository;

    @InjectMocks
    private SelfFeedbackService selfFeedbackService;

    private User user;
    private Question question;
    private Answer answer;
    private SelfFeedback selfFeedback;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        selfFeedback = createSelfFeedback(answer);
    }

    @DisplayName("SelfFeedback을 저장한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveSelfFeedback() {
        // given
        SelfFeedbackRequestDto.SaveSelfFeedbackDto saveSelfFeedbackDto = SelfFeedbackRequestDto.SaveSelfFeedbackDto.builder()
                .feedback("feedback")
                .build();

        // when
        given(answerCheckService.getAnswerByAnswerId(answer.getId())).willReturn(answer);
        given(selfFeedbackCheckService.isExistsSelfFeedbackWithAnswerId(answer.getId())).willReturn(false);
        given(selfFeedbackRepository.save(any())).willReturn(selfFeedback);

        selfFeedbackService.saveSelfFeedback(answer.getId(), saveSelfFeedbackDto);

        // then
        verify(selfFeedbackRepository, times(1)).save(any());
    }
}