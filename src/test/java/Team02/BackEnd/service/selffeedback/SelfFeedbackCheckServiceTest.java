package Team02.BackEnd.service.selffeedback;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createSelfFeedback;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import Team02.BackEnd.validator.SelfFeedbackValidator;
import java.util.List;
import java.util.Optional;
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
    @Mock
    private SelfFeedbackValidator selfFeedbackValidator;

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
        user = createUser(1L);
        question = createQuestion(1L, "description");
        answer = createAnswer(1L, user, question);
        selfFeedback = createSelfFeedback(1L, answer);
    }

    @DisplayName("가장 최근 셀프 피드백을 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getLatestSelfFeedback() {
        // given
        List<Long> answerIds = List.of(answer.getId());

        // when
        given(userCheckService.getUserIdByToken(accessToken)).willReturn(user.getId());
        given(answerCheckService.getAnswerIdsByUserIdWithSize(user.getId(), 1)).willReturn(answerIds);
        given(selfFeedbackRepository.findSelfFeedbackText(answer.getId())).willReturn(
                Optional.of(selfFeedback.getFeedback()));

        String findSelfFeedback = selfFeedbackCheckService.getLatestSelfFeedbackText(accessToken);

        // then
        assertThat(findSelfFeedback).isEqualTo(selfFeedback.getFeedback());
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
        given(selfFeedbackRepository.existsByAnswerId(answer.getId())).willReturn(true);
        Boolean isExistsSelfFeedback = selfFeedbackCheckService.isExistsSelfFeedbackWithAnswerId(answer.getId());

        // then
        assertThat(isExistsSelfFeedback).isTrue();
    }
}