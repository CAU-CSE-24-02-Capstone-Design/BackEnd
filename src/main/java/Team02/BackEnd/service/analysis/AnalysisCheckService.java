package Team02.BackEnd.service.analysis;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnalysisHandler;
import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnalysisRepository;
import Team02.BackEnd.service.user.UserCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnalysisCheckService {

    private final UserCheckService userCheckService;
    private final AnalysisRepository analysisRepository;

    public String getAnalysis(final String accessToken) {
        User user = userCheckService.getUserByToken(accessToken);
        Analysis analysis = analysisRepository.findMostRecentAnalysisByUserId(user.getId());
        validateAnalysisIsNotNull(analysis);
        return analysis.getAnalysisText();
    }

    private void validateAnalysisIsNotNull(final Analysis analysis) {
        if (analysis == null) {
            throw new AnalysisHandler(ErrorStatus._ANALYSIS_NOT_FOUND);
        }
    }
}
