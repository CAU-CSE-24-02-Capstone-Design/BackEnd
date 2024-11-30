package Team02.BackEnd.service.statistics;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.statisticsDto.StatisticsRequestDto.GetStatisticsDto;
import Team02.BackEnd.repository.StatisticsRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    private User user;
    private Question qusetion;
    private Answer answer;

    @BeforeEach
    void setUp() {
        user = createUser();
        qusetion = createQuestion();
        answer = createAnswer(user, qusetion);
    }

    @DisplayName("통계 정보를 저장한다")
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
        given(answerCheckService.getAnswerByAnswerId(answer.getId())).willReturn(answer);
        statisticsService.saveStatistics(getStatisticsDto);

        // then
        verify(statisticsRepository, times(1)).saveAndFlush(any());
    }
}