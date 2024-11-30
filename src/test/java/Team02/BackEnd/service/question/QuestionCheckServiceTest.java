package Team02.BackEnd.service.question;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.QuestionHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.QuestionRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
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
    private UserCheckService userCheckService;
    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private FeedbackCheckService feedbackCheckService;
    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionCheckService questionCheckService;

    private String accessToken;
    private Long level;
    private User user;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
        level = 1L;
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
    }

    @DisplayName("사용자의 최신 질문 가져오기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserQuestion() {
        // given

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.getLatestAnswerByUser(user)).willReturn(Optional.of(answer));
        given(feedbackCheckService.isFeedbackExistsWithAnswer(answer)).willReturn(true);
        given(questionRepository.findByQuestionIndexAndLevel(user.getQuestionNumber(level), level)).willReturn(
                question);

        Question userQuestion = questionCheckService.getUserQuestion(accessToken, level);

        // then
        assertThat(question).isEqualTo(userQuestion);
    }

    @DisplayName("사용자의 최신 질문 재사용하기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void reuseUserQuestion() {
        // given

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.getLatestAnswerByUser(user)).willReturn(Optional.of(answer));
        given(feedbackCheckService.isFeedbackExistsWithAnswer(answer)).willReturn(false);
        given(questionRepository.findByQuestionIndexAndLevel(user.getQuestionNumber(level), level)).willReturn(
                question);

        Question userQuestion = questionCheckService.getUserQuestion(accessToken, level);

        // then
        assertThat(question).isEqualTo(userQuestion);
    }

    @DisplayName("사용자의 질문 번호에 대한 질문을 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getQuestion() {
        // given

        // when
        given(questionRepository.findByQuestionIndexAndLevel(user.getQuestionNumber(level), level)).willReturn(
                question);
        Question userQuestion = questionCheckService.getQuestionByUserQNumberAndLevel(user.getQuestionNumber(level),
                level);

        // then
        assertThat(question).isEqualTo(userQuestion);
    }

    @DisplayName("사용자의 질문 번호에 대한 질문이 없으면 _QUESTION_NOT_FOUND 에러를 반환한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getNoQuestion() {
        // given

        // when
        given(questionRepository.findByQuestionIndexAndLevel(user.getQuestionNumber(level), level)).willReturn(null);
        QuestionHandler exception = assertThrows(QuestionHandler.class, () -> {
            questionCheckService.getQuestionByUserQNumberAndLevel(user.getQuestionNumber(level), level);
        });

        // then
        assertThat(ErrorStatus._QUESTION_NOT_FOUND.getCode()).isEqualTo(exception.getCode().getReason().getCode());
    }
}