package Team02.BackEnd.service.statistics;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.StatisticsHandler;
import Team02.BackEnd.converter.StatisticsConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.domain.oauth.User;
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
        User user = userCheckService.getUserByToken(accessToken);
        log.info("사용자의 모든 스피치 통계 가져오기, email : {}", user.getEmail());
        return answerCheckService.getAnswersByUser(user).stream()
                .filter(this::isStatisticsExistsWithAnswer)
                .map(answer -> {
                    Statistics statistics = getStatisticsByAnswer(answer);
                    return StatisticsConverter.toGetStatisticsDto(statistics, answer);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GetStatisticsDto> getUserStatisticsByLevel(final String accessToken, final Long level) {
        User user = userCheckService.getUserByToken(accessToken);
        log.info("사용자의 난이도 별 스피치 통계 가져오기, level : {}, email : {}", level, user.getEmail());
        return answerCheckService.getAnswersByUser(user).stream()
                .filter(this::isStatisticsExistsWithAnswer)
                .filter(answer -> answerCheckService.checkSpeechLevel(answer, level))
                .map(answer -> {
                    Statistics statistics = getStatisticsByAnswer(answer);
                    return StatisticsConverter.toGetStatisticsDto(statistics, answer);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean isStatisticsExistsWithAnswer(final Answer answer) {
        return statisticsRepository.findByAnswerId(answer.getId()).isPresent();
    }

    @Transactional(readOnly = true)
    public Statistics getStatisticsByAnswer(final Answer answer) {
        Statistics statistics = statisticsRepository.findByAnswerId(answer.getId()).orElse(null);
        validateStatistics(statistics);
        return statistics;
    }

    private void validateStatistics(final Statistics statistics) {
        if (statistics == null) {
            throw new StatisticsHandler(ErrorStatus._STATISTICS_NOT_FOUND);
        }
    }
}
