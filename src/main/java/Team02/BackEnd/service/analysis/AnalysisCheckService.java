package Team02.BackEnd.service.analysis;

import static Team02.BackEnd.constant.Constants.BASE_TIME_ZONE;
import static Team02.BackEnd.constant.Constants.NEW_TIME_ZONE;

import Team02.BackEnd.converter.AnalysisConverter;
import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.analysisDto.AnalysisApiDataDto;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisDto;
import Team02.BackEnd.dto.userDto.UserDto.UserAnswerIndexDto;
import Team02.BackEnd.repository.AnalysisRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import Team02.BackEnd.validator.AnalysisValidator;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public class AnalysisCheckService {

    private static final int NUMBER_OF_USER_SPEECH = 7;

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final FeedbackCheckService feedbackCheckService;
    private final AnalysisRepository analysisRepository;
    private final AnalysisValidator analysisValidator;

    public boolean canSaveAnalysis(final String accessToken) {
        UserAnswerIndexDto userAnswerIndexDto = userCheckService.getUserAnswerIndexByToken(accessToken);
        List<Long> latestAnswerIds = answerCheckService.getAnswerIdsByUserIdWithSize(userAnswerIndexDto.getId(),
                        NUMBER_OF_USER_SPEECH)
                .stream()
                .filter(feedbackCheckService::isFeedbackExistsWithAnswerId)
                .toList();
        if (latestAnswerIds.size() != NUMBER_OF_USER_SPEECH) {
            return false;
        }
        return latestAnswerIds.stream()
                .noneMatch(id -> userAnswerIndexDto.getId().equals(id));
    }

    public GetAnalysisDto getAnalysis(final String accessToken) {
        Long userId = userCheckService.getUserIdByToken(accessToken);
        List<String> answerDates = answerCheckService.getLatestAnswerIdDtosByUserIdWithSize(userId,
                        NUMBER_OF_USER_SPEECH).stream()
                .filter(data -> feedbackCheckService.isFeedbackExistsWithAnswerId(data.getId()))
                .map(data -> data.getCreatedAt().atZone(ZoneId.of(BASE_TIME_ZONE))
                        .withZoneSameInstant(ZoneId.of(NEW_TIME_ZONE)).toLocalDate().toString())
                .toList();
        Pageable pageable = PageRequest.of(0, 1);
        Analysis analysis = analysisRepository.findLatestAnalysisByUserIdWithSize(userId, pageable).get(0);
        analysisValidator.validateAnalysis(analysis);
        return AnalysisConverter.toGetAnalysisDto(analysis.getAnalysisTextAsList(), answerDates);
    }

    public AnalysisApiDataDto getDataForAnalysisApi(final String accessToken) {
        User user = userCheckService.getUserByToken(accessToken);
        List<String> questions = answerCheckService.findQuestionDescriptionsByUser(user, NUMBER_OF_USER_SPEECH);
        List<String> beforeScripts = feedbackCheckService.findBeforeScriptByUser(user, NUMBER_OF_USER_SPEECH);
        return new AnalysisApiDataDto(user, questions, beforeScripts);
    }
}
