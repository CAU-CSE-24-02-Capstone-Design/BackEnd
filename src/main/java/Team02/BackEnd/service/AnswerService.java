package Team02.BackEnd.service;

import Team02.BackEnd.converter.AnswerConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.exception.validator.AnswerValidator;
import Team02.BackEnd.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final UserService userService;
    private final AnswerRepository answerRepository;

    public Long getAnswerId(String accessToken, Question question) {
        User user = userService.getUserByToken(accessToken);
        Answer answer = AnswerConverter.toAnswer(user, question);

        answerRepository.save(answer);

        return answer.getId();
    }

    public Answer getAnswerByUserId(Long userId) {
        Answer answer = answerRepository.findByUserId(userId);
        AnswerValidator.validateAnswerIsNotNull(answer);
        return answer;
    }

    public Answer getAnswerByAnswerId(Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        AnswerValidator.validateAnswerIsNotNull(answer);
        return answer;
    }
}
