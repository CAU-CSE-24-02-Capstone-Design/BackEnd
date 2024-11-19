package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnswerHandler;
import Team02.BackEnd.converter.AnswerConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnswerRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserService userService;

    public Long getAnswerId(final String accessToken, final Question question) {
        User user = userService.getUserByToken(accessToken);
        Answer answer = AnswerConverter.toAnswer(user, question);

        answerRepository.save(answer);
        return answer.getId();
    }

    public List<Answer> getAnswersByUserId(final Long userId) {
        List<Answer> answers = answerRepository.findByUserId(userId);
        answers.forEach(this::validateAnswerIsNotNull);
        return answers;
    }

    public Answer getAnswerByAnswerId(final Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        validateAnswerIsNotNull(answer);
        return answer;
    }

    public boolean doAnswerToday(final String accessToken) {
        User user = userService.getUserByToken(accessToken);
        return getAnswersByUserId(user.getId()).stream()
                .anyMatch(answer -> answer.getCreatedAt().toLocalDate().equals(LocalDate.now()));
    }

    public void saveAnswerEvaluation(final Long answerId, final int evaluation) {
        Answer answer = getAnswerByAnswerId(answerId);
        answer.updateEvaluation(evaluation);
        answerRepository.save(answer);
    }

    public int getAnswerEvaluation(final Long answerId) {
        Answer answer = getAnswerByAnswerId(answerId);
        return answer.getEvaluation();
    }

    private void validateAnswerIsNotNull(final Answer answer) {
        if (answer == null) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
    }
}
