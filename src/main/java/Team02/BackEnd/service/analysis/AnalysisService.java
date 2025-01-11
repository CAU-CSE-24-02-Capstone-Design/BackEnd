package Team02.BackEnd.service.analysis;

import Team02.BackEnd.converter.AnalysisConverter;
import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnalysisRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AnalysisService {

    private final AnalysisRepository analysisRepository;

//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public void saveAnalysis(final String accessToken) {
//        User user = userCheckService.getUserByToken(accessToken);
//        List<String> questions = answerCheckService.findQuestionDescriptionsByUser(user, NUMBER_OF_USER_SPEECH);
//        List<String> beforeScripts = feedbackCheckService.findBeforeScriptByUser(user, NUMBER_OF_USER_SPEECH);
////        GetAnalysisFromFastApiDto response = analysisApiService.getAnalysisFromFastApi(accessToken, questions,
////                beforeScripts);
//        List<List<String>> dummyAnalysisText = List.of(
//                List.of("Dummy analysis line 1 for user", "Dummy analysis line 2 for user"),
//                List.of("Dummy analysis line 3 for user", "Dummy analysis line 4 for user")
//        );
//        GetAnalysisFromFastApiDto response = GetAnalysisFromFastApiDto.builder()
//                .analysisText(dummyAnalysisText)
//                .build();
//        Analysis analysis = AnalysisConverter.toAnalysis(response.getAnalysisText(), user);
//        analysisRepository.save(analysis);
//        log.info("사용자의 일주일 분석 리포트 저장, analysisId : {}", analysis.getId());
//    }

    public void saveAnalysis(final List<List<String>> analysisTexts, final User user) {
        Analysis analysis = AnalysisConverter.toAnalysis(analysisTexts, user);
        analysisRepository.save(analysis);
        log.info("사용자의 일주일 분석 리포트 저장, analysisId : {}", analysis.getId());
    }
}
