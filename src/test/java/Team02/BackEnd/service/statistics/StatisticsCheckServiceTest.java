package Team02.BackEnd.service.statistics;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createStatistics;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.StatisticsHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto;
import Team02.BackEnd.repository.StatisticsRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
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
class StatisticsCheckServiceTest {

    @Mock
    private UserCheckService userCheckService;
    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private StatisticsCheckService statisticsCheckService;

    private String accessToken;
    private User user;
    private Question question;
    private Answer answer;
    private Statistics statistics;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        statistics = createStatistics(answer);
    }

    @DisplayName("통계 데이터 가져오기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserStatistics() {
        // given
        List<Answer> answers = List.of(answer);

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.getAnswersByUser(user)).willReturn(answers);
        given(statisticsRepository.findByAnswerId(answer.getId())).willReturn(Optional.of(statistics));

        List<StatisticsResponseDto.GetStatisticsDto> getStatistics = statisticsCheckService.getUserStatistics(
                accessToken);

        // then
        assertThat(getStatistics.size()).isEqualTo(1);
        assertThat(getStatistics.get(0).getGantourCount()).isEqualTo(statistics.getGantourCount());
        assertThat(getStatistics.get(0).getSilentTime()).isEqualTo(statistics.getSilentTime());
    }

    @DisplayName("난이도 별 통계 데이터 가져오기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserStatisticsByLevel() {
        // given
        List<Answer> answers = List.of(answer);

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.getAnswersByUser(user)).willReturn(answers);
        given(answerCheckService.checkSpeechLevel(answer, 1L)).willReturn(true);
        given(statisticsRepository.findByAnswerId(answer.getId())).willReturn(Optional.of(statistics));

        List<StatisticsResponseDto.GetStatisticsDto> getStatistics = statisticsCheckService.getUserStatisticsByLevel(
                accessToken, 1L);

        // then
        assertThat(getStatistics.size()).isEqualTo(1);
        assertThat(getStatistics.get(0).getGantourCount()).isEqualTo(statistics.getGantourCount());
        assertThat(getStatistics.get(0).getSilentTime()).isEqualTo(statistics.getSilentTime());
    }

    @DisplayName("Answer에 대해 통계가 존재하는지 확인한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void isStatisticsExistsWithAnswer() {
        // given

        // when
        given(statisticsRepository.findByAnswerId(answer.getId())).willReturn(Optional.of(statistics));
        Boolean isExistsStatistics = statisticsCheckService.isStatisticsExistsWithAnswer(answer);

        // then
        assertThat(isExistsStatistics).isTrue();
    }

    @DisplayName("Answer에 대해 통계를 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getStatisticsByAnswer() {
        // given

        // when
        given(statisticsRepository.findByAnswerId(answer.getId())).willReturn(Optional.of(statistics));
        Statistics findStatistics = statisticsCheckService.getStatisticsByAnswer(answer);

        // then
        assertThat(findStatistics).isEqualTo(statistics);
    }

    @DisplayName("Answer에 대해 통계가 없으면 _STATISTICS_NOT_FOUND 에러르 반환한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getNoStatisticsByAnswer() {
        // given

        // when
        given(statisticsRepository.findByAnswerId(answer.getId())).willReturn(Optional.empty());
        StatisticsHandler exception = assertThrows(StatisticsHandler.class, () -> {
            statisticsCheckService.getStatisticsByAnswer(answer);
        });

        // then
        assertThat(ErrorStatus._STATISTICS_NOT_FOUND.getCode()).isEqualTo(exception.getCode().getReason().getCode());
    }
}