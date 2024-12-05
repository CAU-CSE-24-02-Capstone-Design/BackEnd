package Team02.BackEnd.service.analysis;

import static Team02.BackEnd.constant.Constants.BASE_TIME_ZONE;
import static Team02.BackEnd.constant.Constants.NEW_TIME_ZONE;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnalysisHandler;
import Team02.BackEnd.converter.AnalysisConverter;
import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisDto;
import Team02.BackEnd.repository.AnalysisRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnalysisCheckService {
    private static final int NUMBER_OF_USER_SPEECH = 7;

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final FeedbackCheckService feedbackCheckService;
    private final AnalysisRepository analysisRepository;

    public boolean canSaveAnalysis(final String accessToken) {
        User user = userCheckService.getUserByToken(accessToken);
        List<Answer> latestAnswers = answerCheckService.getAnswerByUserWithSize(user, NUMBER_OF_USER_SPEECH).stream()
                .filter(feedbackCheckService::isFeedbackExistsWithAnswer)
                .toList();
        if (latestAnswers.size() != NUMBER_OF_USER_SPEECH) {
            return false;
        }
        return latestAnswers.stream()
                .map(Answer::getId)
                .noneMatch(id -> user.getAnalyzeCompleteAnswerIndex().equals(id));
    }

    public GetAnalysisDto getAnalysis(final String accessToken) {
        User user = userCheckService.getUserByToken(accessToken);
        List<String> answerDates = answerCheckService.getAnswerByUserWithSize(user, NUMBER_OF_USER_SPEECH).stream()
                .filter(feedbackCheckService::isFeedbackExistsWithAnswer)
                .map(answer -> answer.getCreatedAt().atZone(ZoneId.of(BASE_TIME_ZONE))
                        .withZoneSameInstant(ZoneId.of(NEW_TIME_ZONE)).toLocalDate().toString())
                .toList();
        Pageable pageable = PageRequest.of(0, 1);
        Page<Analysis> analysisPage = analysisRepository.findMostRecentAnalysisByUserId(user.getId(), pageable);
        Optional<Analysis> analysis = analysisPage.stream().findFirst();
        validateAnalysisIsNotNull(analysis.get());
        return AnalysisConverter.toGetAnalysisDto(analysis.get().getAnalysisTextAsList(), answerDates);
    }

    private void validateAnalysisIsNotNull(final Analysis analysis) {
        if (analysis == null) {
            throw new AnalysisHandler(ErrorStatus._ANALYSIS_NOT_FOUND);
        }
    }
}
