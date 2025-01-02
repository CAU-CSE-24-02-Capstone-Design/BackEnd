package Team02.BackEnd.service.answer;

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
import Team02.BackEnd.service.user.UserCheckService;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private AnswerCheckService answerCheckService;
    @Mock
    private FeedbackCheckService feedbackCheckService;

    @InjectMocks
    private AnswerService answerService;

    private Long level;
    private Long expectedAnswerId;
    private User user;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        level = 1L;
        expectedAnswerId = 1L;
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
    }

    @DisplayName("새로운 Answer 엔티티를 생성한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void createNewAnswer() {
        // given

        // when
        Long answerId = answerService.createAnswer(user, question, Optional.of(answer), level);
        // then
        assertThat(answerId).isEqualTo(expectedAnswerId);
    }

    @DisplayName("기존 Answer 엔티티를 재사용한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void reuseAnswer() {
        // given

        // when
        given(feedbackCheckService.isFeedbackExistsWithAnswer(answer)).willReturn(false);
        Long answerId = answerService.createAnswer(user, question, Optional.of(answer), level);

        // then
        assertThat(answerId).isEqualTo(expectedAnswerId);
    }

    @DisplayName("스피치에 대한 셀프 평가 점수를 저장한다")
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

    private Answer createAnswer(final User user, final Question question) {
        return Answer.builder()
                .id(1L)
                .user(user)
                .question(question)
                .evaluation(1)
                .createdAt(LocalDateTime.of(2024, 11, 20, 15, 30)
                        .atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
                .build();
    }
}