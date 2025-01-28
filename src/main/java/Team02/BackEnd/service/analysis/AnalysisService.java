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

    public void saveAnalysis(final List<List<String>> analysisTexts, final User user) {
        Analysis analysis = AnalysisConverter.toAnalysis(analysisTexts, user);
        analysisRepository.save(analysis);
        log.info("사용자의 일주일 분석 리포트 저장, user : {}", user.getId());
    }
}
