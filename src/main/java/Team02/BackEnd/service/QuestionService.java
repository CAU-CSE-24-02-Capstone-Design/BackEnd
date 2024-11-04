package Team02.BackEnd.service;

import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.exception.validator.QuestionValidator;
import Team02.BackEnd.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final UserService userService;

    private final QuestionRepository questionRepository;

    public Question getUserQuestion(String accessToken) {
        User user = userService.getUserByToken(accessToken);
        Question question = this.getQuestionByUserQNumber(user.getQuestionNumber());

        user.updateQuestionNumber();

        return question;
    }

    public Question getQuestionByUserQNumber(Long questionNumber) {
        Question question = questionRepository.findByQuestionIndex(questionNumber);
        QuestionValidator.validateQuestionIsNotNull(question);
        return question;
    }
}
