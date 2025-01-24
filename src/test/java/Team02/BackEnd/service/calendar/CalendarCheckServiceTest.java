package Team02.BackEnd.service.calendar;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.answerDto.AnswerDto;
import Team02.BackEnd.dto.answerDto.AnswerDto.AnswerIdDto;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
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
        year = "2025";
        month = "1";
        accessToken = "accessToken";
        user = createUser(1L);
        question = createQuestion(1L, "description");
        answer = createAnswer(1L, user, question);
    }

    @DisplayName("해당 연, 월에 대한 스피치 기록을 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getDatesWhenUserDid() {
        // given
        AnswerDto.AnswerIdDto answerIdDto = new AnswerIdDto(answer.getId(), answer.getCreatedAt());
        List<AnswerDto.AnswerIdDto> answersInPeriod = List.of(answerIdDto);
        // when
        given(userCheckService.getUserIdByToken(accessToken)).willReturn(user.getId());
        given(answerCheckService.findAnswerIdDtosByUserAndYearAndMonth(user.getId(), year, month)).willReturn(
                answersInPeriod);
        given(feedbackCheckService.isFeedbackExistsWithAnswerId(answer.getId())).willReturn(true);

        Long[] answerIdDidThisPeriod = calendarCheckService.getDatesWhenUserDid(accessToken, year, month);

        // then
        assertThat(answerIdDidThisPeriod[20]).isEqualTo(1L);
    }
}