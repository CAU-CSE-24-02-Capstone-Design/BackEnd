package Team02.BackEnd.service.statistics;

import Team02.BackEnd.dto.statisticsDto.StatisticsRequestDto.GetStatisticsDto;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsManager {

    private final StatisticsCheckService statisticsCheckService;
    private final StatisticsService statisticsService;

    public void saveStatistics(final GetStatisticsDto getStatisticsDto) {
        statisticsService.saveStatistics(getStatisticsDto);
    }

    public List<StatisticsResponseDto.GetStatisticsDto> getUserStatistics(final String accessToken) {
        return statisticsCheckService.getUserStatistics(accessToken);
    }

    public List<StatisticsResponseDto.GetStatisticsDto> getUserStatisticsByLevel(final String accessToken,
                                                                                 final Long level) {
        return statisticsCheckService.getUserStatisticsByLevel(accessToken, level);
    }
}
