package Team02.BackEnd.service;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_HEADER_NAME;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.StatisticsHandler;
import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.converter.StatisticsConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.feedbackDto.FeedbackRequestDto.GetComponentToMakeFeedbackDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import Team02.BackEnd.dto.statisticsDto.StatisticsRequestDto.GetStatisticsDto;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto;
import Team02.BackEnd.repository.StatisticsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {


    private final StatisticsRepository statisticsRepository;
    private final UserService userService;
    private final AnswerService answerService;

    public void saveStatistics(final GetStatisticsDto getStatisticsDto) {
        Answer answer = answerService.getAnswerByAnswerId(getStatisticsDto.getAnswerId());
        Statistics statistics = Statistics.builder()
                .gantourCount(getStatisticsDto.getGantourCount())
                .silentTime(getStatisticsDto.getSilentTime())
                .answer(answer)
                .build();
        statisticsRepository.saveAndFlush(statistics);
        log.info("사용자 스피치에 대한 통계 생성, statisticsId : {}", statistics.getId());
    }

    public List<StatisticsResponseDto.GetStatisticsDto> getFilterStatistics(final String accessToken) {
        User user = userService.getUserByToken(accessToken);
        log.info("사용자의 모든 스피치 통계 가져오기, email : {}", user.getEmail());
        return answerService.getAnswersByUserId(user.getId()).stream()
                .map(this::getStatisticsByAnswerId)
                .map(StatisticsConverter::toGetStatisticsDto)
                .toList();
    }

    public String getAnalysis(String accessToken) {
        User user = userService.getUserByToken(accessToken);
        //인덱스 저장해놓고 거기서부터 가져오기 시작하게

    }

    private ResponseEntity<GetFeedbackToFastApiDto> getAnalysisFromFastApi(final String accessToken,
                                                                           final User user,
                                                                           final Long answerId) {
        String beforeAudioLink = feedback.getBeforeAudioLink();
        List<String> pastAudioLinks = getPastAudioLinks(user);  // MAX 5개, 5개 이하면 다 가져옴

        GetComponentToMakeFeedbackDto getComponentToMakeFeedbackDto =
                FeedbackConverter.toGetComponentToMakeFeedback(beforeAudioLink, user, pastAudioLinks,
                        answerId);
        return makeApiCallToFastApi(accessToken, getComponentToMakeFeedbackDto);
    }

    private ResponseEntity<GetFeedbackToFastApiDto> makeApiCallToFastApi(final String accessToken,
                                                                         final GetComponentToMakeFeedbackDto getComponentToMakeFeedbackDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(ACCESS_TOKEN_HEADER_NAME, ACCESS_TOKEN_PREFIX + accessToken);
        HttpEntity<GetComponentToMakeFeedbackDto> request = new HttpEntity<>(getComponentToMakeFeedbackDto,
                headers);
        return restTemplate.postForEntity(FASTAPI_API_URL_LOCAL, request, GetFeedbackToFastApiDto.class);
    }

    private Statistics getStatisticsByAnswerId(final Answer answer) {
        Statistics statistics = statisticsRepository.findByAnswerId(answer.getId());
        validateStatistics(statistics);
        return statistics;
    }

    private void validateStatistics(final Statistics statistics) {
        if (statistics == null) {
            throw new StatisticsHandler(ErrorStatus._STATISTICS_NOT_FOUND);
        }
    }
}
