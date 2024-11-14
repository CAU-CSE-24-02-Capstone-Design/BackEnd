package Team02.BackEnd.service;

import Team02.BackEnd.converter.AnswerConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.exception.validator.AnswerValidator;
import Team02.BackEnd.repository.AnswerRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {

    private final UserService userService;
    private final AnswerRepository answerRepository;

    public Long getAnswerId(String accessToken, Question question) {
        User user = userService.getUserByToken(accessToken);
        Answer answer = AnswerConverter.toAnswer(user, question);

        answerRepository.save(answer);

        return answer.getId();
    }

    public List<Answer> getAnswersByUserId(Long userId) {
        List<Answer> answers = answerRepository.findByUserId(userId);
        answers.forEach(AnswerValidator::validateAnswerIsNotNull);
        return answers;
    }

    public Answer getLatestAnswerByUserId(Long userId) {
        List<Answer> answers = answerRepository.findByUserId(userId);
        Optional<Answer> answer = answers.stream().max(Comparator.comparing(Answer::getCreatedAt));
        AnswerValidator.validateAnswerIsNotNull(answer.orElse(null));
        return answer.orElse(null);
    }

    public Answer getAnswerByAnswerId(Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        AnswerValidator.validateAnswerIsNotNull(answer);
        return answer;
    }
}
