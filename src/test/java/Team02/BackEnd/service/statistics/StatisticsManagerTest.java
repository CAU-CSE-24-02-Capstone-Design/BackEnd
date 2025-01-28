package Team02.BackEnd.service.statistics;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createStatistics;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.statisticsDto.StatisticsRequestDto.GetStatisticsDto;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class StatisticsManagerTest {

    @Mock
    private StatisticsService statisticsService;

    @Mock
    private StatisticsCheckService statisticsCheckService;

    @InjectMocks
    private StatisticsManager statisticsManager;

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

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveStatistics() {
        // given
        GetStatisticsDto getStatisticsDto = GetStatisticsDto.builder()
                .gantourCount(1L)
                .silentTime(5.2)
                .answerId(answer.getId())
                .build();

        // when
        statisticsManager.saveStatistics(getStatisticsDto);

        // then
        verify(statisticsService, times(1)).saveStatistics(getStatisticsDto);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserStatistics() {
        // given

        // when
        List<StatisticsResponseDto.GetStatisticsDto> getStatisticsDtoList = statisticsManager.getUserStatistics(
                accessToken);

        // then
        verify(statisticsCheckService, times(1)).getUserStatistics(accessToken);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserStatisticsByLevel() {
        // given

        // when
        List<StatisticsResponseDto.GetStatisticsDto> getStatisticsDtoList = statisticsManager.getUserStatisticsByLevel(
                accessToken, 1L);

        // then
        verify(statisticsCheckService, times(1)).getUserStatisticsByLevel(accessToken, 1L);
    }
}