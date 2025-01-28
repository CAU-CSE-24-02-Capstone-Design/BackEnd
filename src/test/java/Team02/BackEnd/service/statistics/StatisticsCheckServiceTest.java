package Team02.BackEnd.service.statistics;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createStatistics;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.answerDto.AnswerDto.AnswerIdDto;
import Team02.BackEnd.dto.statisticsDto.StatisticsDto.StatisticsDataDto;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto;
import Team02.BackEnd.repository.StatisticsRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import Team02.BackEnd.validator.StatisticsValidator;
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
    @Mock
    private StatisticsValidator statisticsValidator;

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
        AnswerIdDto answerIdDto = AnswerIdDto.builder()
                .id(answer.getId())
                .createdAt(answer.getCreatedAt())
                .build();
        List<AnswerIdDto> answerIdDtos = List.of(answerIdDto);

        StatisticsDataDto statisticsDataDto = StatisticsDataDto.builder()
                .gantourCount(statistics.getGantourCount())
                .silentTime(statistics.getSilentTime())
                .build();

        // when
        given(userCheckService.getUserIdByToken(accessToken)).willReturn(user.getId());
        given(answerCheckService.getAnswerIdDtosByUserId(user.getId())).willReturn(answerIdDtos);
        given(statisticsRepository.existsByAnswerId(answer.getId())).willReturn(true);
        given(statisticsRepository.findStatisticsDataDtoByAnswerId(answer.getId())).willReturn(
                Optional.of(statisticsDataDto));

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
        AnswerIdDto answerIdDto = AnswerIdDto.builder()
                .id(answer.getId())
                .createdAt(answer.getCreatedAt())
                .build();
        List<AnswerIdDto> answerIdDtos = List.of(answerIdDto);

        StatisticsDataDto statisticsDataDto = StatisticsDataDto.builder()
                .gantourCount(statistics.getGantourCount())
                .silentTime(statistics.getSilentTime())
                .build();

        // when
        given(userCheckService.getUserIdByToken(accessToken)).willReturn(user.getId());
        given(answerCheckService.getAnswerIdDtosWithLevelByUserId(user.getId(), 1L)).willReturn(answerIdDtos);
        given(statisticsRepository.existsByAnswerId(answer.getId())).willReturn(true);
        given(statisticsRepository.findStatisticsDataDtoByAnswerId(answer.getId())).willReturn(
                Optional.of(statisticsDataDto));

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
    void isStatisticsExistsWithAnswerId() {
        // given

        // when
        given(statisticsRepository.existsByAnswerId(answer.getId())).willReturn(true);
        Boolean isExistsStatistics = statisticsCheckService.isStatisticsExistsWithAnswerId(answer.getId());

        // then
        assertThat(isExistsStatistics).isTrue();
    }

    @DisplayName("Answer에 대해 통계를 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getStatisticsDataDtoByAnswerId() {
        // given
        StatisticsDataDto statisticsDataDto = StatisticsDataDto.builder()
                .gantourCount(statistics.getGantourCount())
                .silentTime(statistics.getSilentTime())
                .build();

        // when
        given(statisticsRepository.findStatisticsDataDtoByAnswerId(answer.getId())).willReturn(
                Optional.of(statisticsDataDto));
        StatisticsDataDto result = statisticsCheckService.getStatisticsDataDtoByAnswerId(answer.getId());

        // then
        assertThat(result).isEqualTo(statisticsDataDto);
    }
}