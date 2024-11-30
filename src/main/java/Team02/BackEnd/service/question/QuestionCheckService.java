package Team02.BackEnd.service.question;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.QuestionHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.QuestionRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionCheckService {

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final FeedbackCheckService feedbackCheckService;
    private final QuestionRepository questionRepository;

    public Question getUserQuestion(final String accessToken, final Long level) {
        User user = userCheckService.getUserByToken(accessToken);
        Optional<Answer> latestAnswer = answerCheckService.getLatestAnswerByUser(user);
        if (latestAnswer.isPresent() && !feedbackCheckService.isFeedbackExistsWithAnswer(latestAnswer.get())) {
            user.minusQuestionNumber(level);
            log.info("사용자가 스피치를 진행하지 않았던 질문 받아오기, questionId : {}", user.getQuestionNumber(level));
        }
        Question question = getQuestionByUserQNumberAndLevel(user.getQuestionNumber(level), level);
        log.info("사용자가 오늘의 질문 받아오기, questionId : {}", question.getId());
        return question;
    }

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
