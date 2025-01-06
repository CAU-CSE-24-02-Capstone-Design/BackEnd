package Team02.BackEnd.service.question;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.QuestionHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.QuestionRepository;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionCheckService {

    private final FeedbackCheckService feedbackCheckService;
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public Question getUserQuestion(final User user, final Optional<Answer> latestAnswer, final Long level) {
        if (latestAnswer.isPresent() && !feedbackCheckService.isFeedbackExistsWithAnswerId(latestAnswer.get().getId())) {
            user.minusQuestionNumber(level);
            log.info("사용자가 스피치를 진행하지 않았던 질문 받아오기, questionId : {}", user.getQuestionNumber(level));
        }
        Question question = this.getQuestionByUserQNumberAndLevel(user.getQuestionNumber(level), level);
        log.info("사용자가 오늘의 질문 받아오기, questionId : {}", question.getId());
        return question;
    }

    @Transactional(readOnly = true)
    public Question getQuestionByUserQNumberAndLevel(final Long questionNumber, final Long level) {
        Question question = questionRepository.findByQuestionIndexAndLevel(questionNumber, level);
        validateQuestionIsNotNull(question);
        return question;
    }

    private void validateQuestionIsNotNull(final Question question) {
        if (question == null) {
            throw new QuestionHandler(ErrorStatus._QUESTION_NOT_FOUND);
        }
    }
}
