package Team02.BackEnd.service.answer;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnswerHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnswerRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnswerCheckService {

    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public List<Answer> getAnswersByUser(final User user) {
        List<Answer> answers = answerRepository.findByUserId(user.getId());
        validateAnswersEmpty(answers);
        return answers;
    }

    @Transactional(readOnly = true)
    public Answer getAnswerByAnswerId(final Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        validateAnswerIsNotNull(answer);
        return answer;
    }

    @Transactional(readOnly = true)
    public List<Answer> findAnswersByUserAndYearAndMonth(final User user, final String year, final String month) {
        List<Answer> answers = answerRepository.findByUserAndYearAndMonth(user, Integer.parseInt(year),
                Integer.parseInt(month));
        validateAnswersEmpty(answers);
        return answers;
    }

    @Transactional(readOnly = true)
    public Optional<Answer> getLatestAnswerByUser(final User user) {
        Pageable pageable = PageRequest.of(0, 1);
        return answerRepository.findLatestAnswerByUser(user, pageable).stream().findFirst();
    }

    @Transactional(readOnly = true)
    public List<Answer> getAnswerByUserWithSize(final User user, final int size) {
        Pageable pageable = PageRequest.of(0, size);
        return answerRepository.findLatestAnswerByUser(user, pageable);
    }

    @Transactional(readOnly = true)
    public Boolean checkSpeechLevel(final Answer answer, final Long level) {
        return Objects.equals(answer.getQuestion().getLevel(), level);
    }

    @Transactional(readOnly = true)
    public List<String> findQuestionDescriptionsByUser(final User user, final int number) {
        Pageable pageable = PageRequest.of(0, number);
        List<Answer> answers = answerRepository.findLatestAnswerByUser(user, pageable);
        user.updateAnalyzeCompleteAnswerIndex(answers.get(0).getId());
        return answers.stream()
                .map(answer -> answer.getQuestion().getDescription())
                .toList();
    }

    private void validateAnswerIsNotNull(final Answer answer) {
        if (answer == null) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
    }

    private void validateAnswersEmpty(final List<Answer> answers) {
        if (answers.isEmpty()) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
    }
}
