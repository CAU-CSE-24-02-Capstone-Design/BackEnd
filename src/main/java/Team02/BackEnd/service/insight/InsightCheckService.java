package Team02.BackEnd.service.insight;

import Team02.BackEnd.repository.InsightRepository;
import Team02.BackEnd.validator.InsightValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public class InsightCheckService {

    private final InsightRepository insightRepository;
    private final InsightValidator insightValidator;

    public List<String> getAiInsight(final Long answerId) {
        List<String> insights = insightRepository.findInsightsByAnswerId(answerId);
        insightValidator.validateInsight(insights);
        log.info("질문에 대한 AI 인사이트 가져오기, answerId : {}", answerId);
        return insights;
    }
}
