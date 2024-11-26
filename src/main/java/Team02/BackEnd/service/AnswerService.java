package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnswerHandler;
import Team02.BackEnd.converter.AnswerConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnswerRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
    private final FeedbackService feedbackService;

    public Long getAnswerId(final String accessToken, final Question question) {
        User user = userService.getUserByToken(accessToken);
        if (isWrongAnswer(user)) {
            return getLatestAnswerByUser(user).get().getId();
        }
        Answer answer = AnswerConverter.toAnswer(user, question);
        user.addQuestionNumber();
        answerRepository.save(answer);
        log.info("answer 엔티티 생성, answerId : {}", answer.getId());
        return answer.getId();
    }

    public boolean isWrongAnswer(final User user) {
        Optional<Answer> latestAnswer = getLatestAnswerByUser(user);
        return latestAnswer.filter(answer -> !feedbackService.isFeedbackExistsWithAnswer(answer)).isPresent();
    }

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

    public void saveAnswerEvaluation(final Long answerId, final int evaluation) {
        Answer answer = getAnswerByAnswerId(answerId);
        answer.updateEvaluation(evaluation);
        answerRepository.save(answer);
        log.info("스피치에 대한 평가 점수 저장, answerId : {}, 점수 : {}", answerId, evaluation);
    }

    public int getAnswerEvaluation(final Long answerId) {
        Answer answer = getAnswerByAnswerId(answerId);
        return answer.getEvaluation();
    }

    private Optional<Answer> getLatestAnswerByUser(final User user) {
        return answerRepository.findLatestAnswerByUser(user);
    }

    private void validateAnswerIsNotNull(final Answer answer) {
        if (answer == null) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
    }
}
