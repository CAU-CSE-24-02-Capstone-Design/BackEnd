package Team02.BackEnd.service.analysis;

import Team02.BackEnd.converter.AnalysisConverter;
import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisFromFastApiDto;
import Team02.BackEnd.repository.AnalysisRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalysisService {
    private static final int NUMBER_OF_USER_SPEECH = 7;

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final FeedbackCheckService feedbackCheckService;
    private final AnalysisApiService analysisApiService;
    private final AnalysisRepository analysisRepository;

    @Transactional
    public void saveAnalysis(final String accessToken) {
        User user = userCheckService.getUserByToken(accessToken);
        List<String> questions = answerCheckService.findQuestionDescriptionsByUser(user, NUMBER_OF_USER_SPEECH);
        List<String> beforeScripts = feedbackCheckService.findBeforeScriptByUser(user, NUMBER_OF_USER_SPEECH);
        GetAnalysisFromFastApiDto response = analysisApiService.getAnalysisFromFastApi(accessToken, questions,
                beforeScripts);
        Analysis analysis = AnalysisConverter.toAnalysis(response.getAnalysisText(), user);
        analysisRepository.save(analysis);
        log.info("사용자의 일주일 분석 리포트 저장, analysisId : {}", analysis.getId());
    }
}
