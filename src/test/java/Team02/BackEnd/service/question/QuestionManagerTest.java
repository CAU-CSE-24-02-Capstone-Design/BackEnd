package Team02.BackEnd.service.question;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.questionDto.QuestionAnswerIdDto;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.answer.AnswerService;
import Team02.BackEnd.service.user.UserCheckService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class QuestionManagerTest {

    @Mock
    private QuestionCheckService questionCheckService;
    @Mock
    private UserCheckService userCheckService;
    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private AnswerService answerService;

    @InjectMocks
    private QuestionManager questionManager;

    private Long level;
    private String accessToken;
    private User user;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        level = 1L;
        accessToken = "accessToken";
        user = createUser(1L);
        question = createQuestion(1L, "description");
        answer = createAnswer(1L, user, question);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserQuestion() {
        // given
        Long newAnswerId = 2L;

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.getLatestAnswerIdByUserId(user.getId())).willReturn(Optional.of(answer.getId()));
        given(questionCheckService.getUserQuestion(user, Optional.of(answer.getId()), level)).willReturn(question);
        given(answerService.createAnswer(user, question, Optional.of(answer.getId()), level)).willReturn(newAnswerId);

        QuestionAnswerIdDto result = questionManager.getUserQuestion(accessToken, level);

        // then
        assertThat(result.answerId()).isEqualTo(newAnswerId);

    }
}