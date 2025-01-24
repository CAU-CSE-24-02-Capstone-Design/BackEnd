package Team02.BackEnd.service.answer;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnswerRepository;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AnswerServiceTest {

    @Mock
    private FeedbackCheckService feedbackCheckService;
    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerService answerService;

    private Long level;
    private User user;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        level = 1L;
        user = createUser(1L);
        question = createQuestion(1L, "description");
        answer = createAnswer(1L, user, question);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void createNewAnswer() {
        // given
        Optional<Long> latestAnswer = Optional.of(answer.getId());

        // when
        given(feedbackCheckService.isFeedbackExistsWithAnswerId(any(Long.class))).willReturn(true);
        given(answerRepository.saveAndFlush(any(Answer.class))).willReturn(answer);

        Long answerId = answerService.createAnswer(user, question, latestAnswer, level);

        // then
        assertThat(answerId).isEqualTo(answer.getId());
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveAnswerEvaluation() {
        // given

        // when
        given(answerCheckService.getAnswerByAnswerId(answer.getId())).willReturn(answer);

        answerService.saveAnswerEvaluation(answer.getId(), 2);

        // then
        assertThat(answer.getEvaluation()).isEqualTo(2);

    }
}