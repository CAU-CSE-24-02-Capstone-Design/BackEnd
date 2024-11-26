package Team02.BackEnd.service.insight;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Insight;
import Team02.BackEnd.repository.InsightRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class InsightCheckService {

    private final AnswerCheckService answerCheckService;
    private final InsightRepository insightRepository;

    public List<String> getAiInsight(final Long answerId) {
        log.info("질문에 대한 AI 인사이트 가져오기, answerId : {}", answerId);
        Answer answer = answerCheckService.getAnswerByAnswerId(answerId);
        return insightRepository.findAllByAnswerId(answer.getId()).stream()
                .map(Insight::getInsight)
                .toList();
    }
}
