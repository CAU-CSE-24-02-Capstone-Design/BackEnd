package Team02.BackEnd.service.question;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.QuestionRepository;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.validator.QuestionValidator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public class QuestionCheckService {

    private final FeedbackCheckService feedbackCheckService;
    private final QuestionRepository questionRepository;
    private final QuestionValidator questionValidator;

    public Question getUserQuestion(final User user, final Optional<Answer> latestAnswer, final Long level) {
        if (latestAnswer.isPresent() && !feedbackCheckService.isFeedbackExistsWithAnswerId(
                latestAnswer.get().getId())) {
            user.minusQuestionNumber(level);
            log.info("사용자가 스피치를 진행하지 않았던 질문 받아오기, questionId : {}", user.getQuestionNumber(level));
        }
        Question question = this.getQuestionByUserQNumberAndLevel(user.getQuestionNumber(level), level);
        log.info("사용자가 오늘의 질문 받아오기, questionId : {}", question.getId());
        return question;
    }

    public Question getQuestionByUserQNumberAndLevel(final Long questionNumber, final Long level) {
        Question question = questionRepository.findByQuestionIndexAndLevel(questionNumber, level);
        questionValidator.validateQuestion(question);
        return question;
    }
}
