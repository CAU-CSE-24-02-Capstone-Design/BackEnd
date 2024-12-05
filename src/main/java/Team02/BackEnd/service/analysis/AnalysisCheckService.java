package Team02.BackEnd.service.analysis;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnalysisHandler;
import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnalysisRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Analysis getAnalysis(final String accessToken) {
        User user = userCheckService.getUserByToken(accessToken);
        Analysis analysis = analysisRepository.findMostRecentAnalysisByUserId(user.getId());
        validateAnalysisIsNotNull(analysis);
        return analysis;
    }

    private void validateAnalysisIsNotNull(final Analysis analysis) {
        if (analysis == null) {
            throw new AnalysisHandler(ErrorStatus._ANALYSIS_NOT_FOUND);
        }
    }
}
