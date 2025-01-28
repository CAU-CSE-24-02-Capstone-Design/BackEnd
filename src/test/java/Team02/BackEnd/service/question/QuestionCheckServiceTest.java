package Team02.BackEnd.service.question;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.QuestionRepository;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.validator.QuestionValidator;
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
class QuestionCheckServiceTest {

    @Mock
    private FeedbackCheckService feedbackCheckService;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private QuestionValidator questionValidator;

    @InjectMocks
    private QuestionCheckService questionCheckService;

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
    void getUserQuestion() {
        // given
        Optional<Long> latestAnswer = Optional.of(answer.getId());

        // when
        given(feedbackCheckService.isFeedbackExistsWithAnswerId(latestAnswer.get())).willReturn(true);
        given(questionRepository.findByQuestionIndexAndLevel(user.getQuestionNumber(level), level)).willReturn(
                question);

        Question userQuestion = questionCheckService.getUserQuestion(user, latestAnswer, level);

        // then
        assertThat(question).isEqualTo(userQuestion);
    }

    @DisplayName("사용자의 질문 번호에 대한 질문을 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getQuestionByUserQNumberAndLevel() {
        // given

        // when
        given(questionRepository.findByQuestionIndexAndLevel(user.getQuestionNumber(level), level)).willReturn(
                question);
        Question userQuestion = questionCheckService.getQuestionByUserQNumberAndLevel(user.getQuestionNumber(level),
                level);

        // then
        assertThat(question).isEqualTo(userQuestion);
    }
}