package Team02.BackEnd.service.statistics;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.StatisticsHandler;
import Team02.BackEnd.converter.StatisticsConverter;
import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto.GetStatisticsDto;
import Team02.BackEnd.repository.StatisticsRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsCheckService {

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final StatisticsRepository statisticsRepository;

    @Transactional(readOnly = true)
    public List<GetStatisticsDto> getUserStatistics(final String accessToken) {
        Long userId = userCheckService.getUserIdByToken(accessToken);
        log.info("사용자의 모든 스피치 통계 가져오기, userId : {}", userId);
        return answerCheckService.getAnswerIdDtosByUserId(userId).stream()
                .filter(data -> this.isStatisticsExistsWithAnswerId(data.getId()))
                .map(data -> {
                    Statistics statistics = this.getStatisticsByAnswerId(data.getId());
                    return StatisticsConverter.toGetStatisticsDto(statistics, data.getCreatedAt());
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GetStatisticsDto> getUserStatisticsByLevel(final String accessToken, final Long level) {
        Long userId = userCheckService.getUserIdByToken(accessToken);
        log.info("사용자의 난이도 별 스피치 통계 가져오기, level : {}, userId : {}", level, userId);
        return answerCheckService.getAnswerLevelDtosWithLevelByUserId(userId).stream()
                .filter(data -> this.isStatisticsExistsWithAnswerId(data.getId()))
                .filter(data -> answerCheckService.checkSpeechLevel(data.getLevel(), level))
                .map(data -> {
                    Statistics statistics = this.getStatisticsByAnswerId(data.getId());
                    return StatisticsConverter.toGetStatisticsDto(statistics, data.getCreatedAt());
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean isStatisticsExistsWithAnswerId(final Long answerId) {
        return statisticsRepository.existsByAnswerId(answerId);
    }

    @Transactional(readOnly = true)
    public Statistics getStatisticsByAnswerId(final Long answerId) {
        Statistics statistics = statisticsRepository.findByAnswerId(answerId).orElse(null);
        validateStatistics(statistics);
        return statistics;
    }

    private <T> void validateStatistics(final T statistics) {
        if (statistics == null) {
            throw new StatisticsHandler(ErrorStatus._STATISTICS_NOT_FOUND);
        }
    }
}
