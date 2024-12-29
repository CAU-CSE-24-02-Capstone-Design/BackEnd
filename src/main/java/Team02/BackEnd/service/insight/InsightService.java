package Team02.BackEnd.service.insight;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Insight;
import Team02.BackEnd.repository.InsightRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class InsightService {

    private final AnswerCheckService answerCheckService;
    private final InsightRepository insightRepository;

    @Transactional
    public void saveAiInsight(final List<String> insights, final Long answerId) {
        Answer answer = answerCheckService.getAnswerByAnswerId(answerId);
        insights.stream()
                .map(insight -> Insight.builder()
                        .insight(insight)
                        .answer(answer)
                        .build())
                .forEach(insightRepository::save);
        log.info("질문에 대한 AI 인사이트 생성, answerId : {}", answerId);
    }
}
