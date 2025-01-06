package Team02.BackEnd.service.calendar;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.answerDto.AnswerDto;
import Team02.BackEnd.dto.answerDto.AnswerDto.AnswerIdDto;
import Team02.BackEnd.oauth.OauthServerType;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
class CalendarCheckServiceTest {

    @Mock
    private UserCheckService userCheckService;
    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private FeedbackCheckService feedbackCheckService;

    @InjectMocks
    private CalendarCheckService calendarCheckService;

    private String year;
    private String month;
    private String accessToken;
    private User user;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        year = "2024";
        month = "11";
        accessToken = "accessToken";
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
    }

    @DisplayName("해당 연, 월에 대한 스피치 기록을 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getDatesWhenUserDid() {
        // given
        AnswerDto.AnswerIdDto answerIdDto = new AnswerIdDto(answer.getId(), answer.getCreatedAt());
        List<AnswerDto.AnswerIdDto> answersInPeriod = List.of(answerIdDto);
        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.findAnswersByUserAndYearAndMonth(user.getId(), year, month)).willReturn(answersInPeriod);
        given(feedbackCheckService.isFeedbackExistsWithAnswerId(answer.getId())).willReturn(true);

        Long[] answerIdDidThisPeriod = calendarCheckService.getDatesWhenUserDid(accessToken, year, month);

        // then
        assertThat(answerIdDidThisPeriod[20]).isEqualTo(1L);
    }

    private User createUser() {
        return User.builder()
                .id(1L)
                .email("tlsgusdn4818@gmail.com")
                .name("Hyun")
                .role(Role.USER)
                .oauthId(new OauthId("1", OauthServerType.GOOGLE))
                .voiceUrl("voiceUrl")
                .level1QuestionNumber(1L)
                .level2QuestionNumber(1L)
                .level3QuestionNumber(1L)
                .build();
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