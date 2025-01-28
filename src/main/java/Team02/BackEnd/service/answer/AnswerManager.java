package Team02.BackEnd.service.answer;

import Team02.BackEnd.domain.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerManager {

    private final AnswerCheckService answerCheckService;
    private final AnswerService answerService;

    public void saveAnswerEvaluation(final Long answerId, final int evaluation) {
        answerService.saveAnswerEvaluation(answerId, evaluation);
    }

    public Answer getAnswerByAnswerId(final Long answerId) {
        return answerCheckService.getAnswerByAnswerId(answerId);
    }
}
