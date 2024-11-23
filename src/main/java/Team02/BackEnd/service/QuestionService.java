package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.QuestionHandler;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;

    public Question getUserQuestion(final String accessToken) {
        User user = userService.getUserByToken(accessToken);
        Question question = getQuestionByUserQNumber(user.getQuestionNumber());
        user.updateQuestionNumber();
        log.info("사용자가 오늘의 질문 받아오기, questionId : {}", question.getId());
        return question;
    }

    private Question getQuestionByUserQNumber(final Long questionNumber) {
        Question question = questionRepository.findByQuestionIndex(questionNumber);
        validateQuestionIsNotNull(question);
        return question;
    }

    private void validateQuestionIsNotNull(final Question question) {
        if (question == null) {
            throw new QuestionHandler(ErrorStatus._QUESTION_NOT_FOUND);
        }
    }
}
