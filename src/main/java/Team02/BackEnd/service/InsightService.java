package Team02.BackEnd.service;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Insight;
import Team02.BackEnd.repository.InsightRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class InsightService {

    private final InsightRepository insightRepository;
    private final AnswerService answerService;

    public void saveAiInsight(final List<String> insights, final Long answerId) {
        Answer answer = answerService.getAnswerByAnswerId(answerId);
        insights.stream()
                .map(insight -> Insight.builder()
                        .insight(insight)
                        .answer(answer)
                        .build())
                .forEach(insightRepository::save);
    }

    public List<String> getAiInsight(final Long answerId) {
        Answer answer = answerService.getAnswerByAnswerId(answerId);
        return insightRepository.findAllByAnswerId(answer.getId()).stream()
                .map(Insight::getInsight)
                .toList();
    }
}
