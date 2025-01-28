package Team02.BackEnd.service.insight;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsightManager {

    private final InsightCheckService insightCheckService;
    private final InsightService insightService;

    public void saveAiInsight(final List<String> insights, final Long answerId) {
        insightService.saveAiInsight(insights, answerId);
    }

    public List<String> getAiInsight(final Long answerId) {
        return insightCheckService.getAiInsight(answerId);
    }
}
