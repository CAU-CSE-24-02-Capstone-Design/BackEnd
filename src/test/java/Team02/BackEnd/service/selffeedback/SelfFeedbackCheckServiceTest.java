package Team02.BackEnd.service.selffeedback;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createSelfFeedback;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.SelfFeedbackHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
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
class SelfFeedbackCheckServiceTest {

    @Mock
    private UserCheckService userCheckService;
    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private SelfFeedbackRepository selfFeedbackRepository;

    @InjectMocks
    private SelfFeedbackCheckService selfFeedbackCheckService;

    private String accessToken;
    private User user;
    private Question question;
    private Answer answer;
    private SelfFeedback selfFeedback;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        selfFeedback = createSelfFeedback(answer);
    }

    @DisplayName("가장 최근 셀프 피드백을 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getLatestSelfFeedback() {
        // given
        List<Answer> answers = List.of(answer);

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.getAnswersByUser(user)).willReturn(answers);
        given(selfFeedbackRepository.findByAnswerId(answer.getId())).willReturn(selfFeedback);

        SelfFeedback findSelfFeedback = selfFeedbackCheckService.getLatestSelfFeedback(accessToken);

        // then
        assertThat(findSelfFeedback).isEqualTo(selfFeedback);
    }

    @DisplayName("가장 최근 셀프 피드백이 없다면 _SELF_FEEDBACK_NO_FOUND 에러를 반환한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getNoLatestSelfFeedback() {
        // given
        List<Answer> answers = List.of(answer);

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.getAnswersByUser(user)).willReturn(answers);
        given(selfFeedbackRepository.findByAnswerId(answer.getId())).willReturn(null);

        SelfFeedbackHandler exception = assertThrows(SelfFeedbackHandler.class, () -> {
            selfFeedbackCheckService.getLatestSelfFeedback(accessToken);
        });

        // then
        assertThat(ErrorStatus._SELF_FEEDBACK_NOT_FOUND.getCode()).isEqualTo(exception.getCode().getReason().getCode());
    }

    @DisplayName("Answer와 연결된 SelfFeedback을 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getSelfFeedbackByAnswerId() {
        // given

        // when
        given(selfFeedbackRepository.findByAnswerId(answer.getId())).willReturn(selfFeedback);
        SelfFeedback findSelfFeedback = selfFeedbackCheckService.getSelfFeedbackByAnswerId(answer.getId());

        // then
        assertThat(findSelfFeedback).isEqualTo(selfFeedback);
    }

    @DisplayName("Answer와 연결된 SelfFeedback이 있는지 확인한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void isExistsSelfFeedbackWithAnswerId() {
        // given

        // when
        given(selfFeedbackRepository.findByAnswerId(answer.getId())).willReturn(selfFeedback);
        Boolean isExistsSelfFeedback = selfFeedbackCheckService.isExistsSelfFeedbackWithAnswerId(answer.getId());

        // then
        assertThat(isExistsSelfFeedback).isTrue();
    }
}