package Team02.BackEnd.service.answer;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnswerHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnswerRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnswerCheckService {

    private final AnswerRepository answerRepository;

    public List<Answer> getAnswersByUser(final User user) {
        List<Answer> answers = answerRepository.findByUserId(user.getId());
        if (answers.isEmpty()) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
        return answers;
    }

    public Answer getAnswerByAnswerId(final Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        validateAnswerIsNotNull(answer);
        return answer;
    }

    public List<Answer> findByUserAndYearAndMonth(final User user, final String year, final String month) {
        return answerRepository.findByUserAndYearAndMonth(user, Integer.parseInt(year),
                Integer.parseInt(month));
    }

    public int getAnswerEvaluation(final Long answerId) {
        Answer answer = getAnswerByAnswerId(answerId);
        return answer.getEvaluation();
    }

    public Optional<Answer> getLatestAnswerByUser(final User user) {
        Pageable pageable = PageRequest.of(0, 1);
        return answerRepository.findLatestAnswerByUser(user, pageable).stream().findFirst();
    }

    private void validateAnswerIsNotNull(final Answer answer) {
        if (answer == null) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
    }
}
