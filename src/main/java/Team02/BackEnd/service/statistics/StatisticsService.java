package Team02.BackEnd.service.statistics;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.dto.statisticsDto.StatisticsRequestDto.GetStatisticsDto;
import Team02.BackEnd.repository.StatisticsRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {

    private final AnswerCheckService answerCheckService;
    private final StatisticsRepository statisticsRepository;

    @Transactional
    public void saveStatistics(final GetStatisticsDto getStatisticsDto) {
        Answer answer = answerCheckService.getAnswerByAnswerId(getStatisticsDto.getAnswerId());
        Statistics statistics = Statistics.builder()
                .gantourCount(getStatisticsDto.getGantourCount())
                .silentTime(getStatisticsDto.getSilentTime())
                .answer(answer)
                .build();
        statisticsRepository.saveAndFlush(statistics);
        log.info("사용자 스피치에 대한 통계 생성, statisticsId : {}", statistics.getId());
    }
}
