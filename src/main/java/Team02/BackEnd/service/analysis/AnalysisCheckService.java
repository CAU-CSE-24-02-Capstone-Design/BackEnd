package Team02.BackEnd.service.analysis;

import static Team02.BackEnd.constant.Constants.BASE_TIME_ZONE;
import static Team02.BackEnd.constant.Constants.NEW_TIME_ZONE;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnalysisHandler;
import Team02.BackEnd.converter.AnalysisConverter;
import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisDto;
import Team02.BackEnd.dto.userDto.UserDto.UserAnswerIndexDto;
import Team02.BackEnd.repository.AnalysisRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnalysisCheckService {
    private static final int NUMBER_OF_USER_SPEECH = 7;

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final FeedbackCheckService feedbackCheckService;
    private final AnalysisRepository analysisRepository;

    @Transactional(readOnly = true)
    public boolean canSaveAnalysis(final String accessToken) {
        UserAnswerIndexDto userAnswerIndexDto = userCheckService.getUserAnswerIndexByToken(accessToken);
        List<Long> latestAnswerIds = answerCheckService.getAnswerIdsByUserWithSize(userAnswerIndexDto.getId(),
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

    @Transactional(readOnly = true)
    public GetAnalysisDto getAnalysis(final String accessToken) {
        Long userId = userCheckService.getUserIdByToken(accessToken);
        List<String> answerDates = answerCheckService.getLatestAnswerIdDtosByUserIdWithSize(userId,
                        NUMBER_OF_USER_SPEECH).stream()
                .filter(data -> feedbackCheckService.isFeedbackExistsWithAnswerId(data.getId()))
                .map(data -> data.getCreatedAt().atZone(ZoneId.of(BASE_TIME_ZONE))
                        .withZoneSameInstant(ZoneId.of(NEW_TIME_ZONE)).toLocalDate().toString())
                .toList();
        Pageable pageable = PageRequest.of(0, 1);
        Analysis analysis = analysisRepository.findMostRecentAnalysisByUserId(userId, pageable).get(0);
        validateAnalysisIsNotNull(analysis);
        return AnalysisConverter.toGetAnalysisDto(analysis.getAnalysisTextAsList(), answerDates);
    }

    private void validateAnalysisIsNotNull(final Analysis analysis) {
        if (analysis == null) {
            throw new AnalysisHandler(ErrorStatus._ANALYSIS_NOT_FOUND);
        }
    }
}
