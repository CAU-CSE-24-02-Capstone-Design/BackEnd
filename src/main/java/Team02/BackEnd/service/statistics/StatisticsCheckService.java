package Team02.BackEnd.service.statistics;

import Team02.BackEnd.converter.StatisticsConverter;
import Team02.BackEnd.dto.statisticsDto.StatisticsDto.StatisticsDataDto;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto.GetStatisticsDto;
import Team02.BackEnd.repository.StatisticsRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import Team02.BackEnd.validator.StatisticsValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public class StatisticsCheckService {

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final StatisticsRepository statisticsRepository;
    private final StatisticsValidator statisticsValidator;

    public List<GetStatisticsDto> getUserStatistics(final String accessToken) {
        Long userId = userCheckService.getUserIdByToken(accessToken);
        log.info("사용자의 모든 스피치 통계 가져오기, userId : {}", userId);
        return answerCheckService.getAnswerIdDtosByUserId(userId).stream()
                .filter(data -> this.isStatisticsExistsWithAnswerId(data.getId()))
                .map(data -> {
                    StatisticsDataDto statisticsDataDto = this.getStatisticsDataDtoByAnswerId(data.getId());
                    return StatisticsConverter.toGetStatisticsDto(statisticsDataDto, data.getCreatedAt());
                })
                .toList();
    }

    public List<GetStatisticsDto> getUserStatisticsByLevel(final String accessToken, final Long level) {
        Long userId = userCheckService.getUserIdByToken(accessToken);
        log.info("사용자의 난이도 별 스피치 통계 가져오기, level : {}, userId : {}", level, userId);
        return answerCheckService.getAnswerIdDtosWithLevelByUserId(userId, level).stream()
                .filter(data -> this.isStatisticsExistsWithAnswerId(data.getId()))
                .map(data -> {
                    StatisticsDataDto statisticsDataDto = this.getStatisticsDataDtoByAnswerId(data.getId());
                    return StatisticsConverter.toGetStatisticsDto(statisticsDataDto, data.getCreatedAt());
                })
                .toList();
    }

    public boolean isStatisticsExistsWithAnswerId(final Long answerId) {
        return statisticsRepository.existsByAnswerId(answerId);
    }

    public StatisticsDataDto getStatisticsDataDtoByAnswerId(final Long answerId) {
        StatisticsDataDto statisticsDataDto = statisticsRepository.findStatisticsDataDtoByAnswerId(answerId)
                .orElse(null);
        statisticsValidator.validateStatistics(statisticsDataDto);
        return statisticsDataDto;
    }
}
