package Team02.BackEnd.service.question;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.questionDto.QuestionAnswerIdDto;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.answer.AnswerService;
import Team02.BackEnd.service.user.UserCheckService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QuestionManager {

    private final QuestionCheckService questionCheckService;
    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final AnswerService answerService;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public QuestionAnswerIdDto getUserQuestion(final String accessToken, final Long level) {
        User user = userCheckService.getUserByToken(accessToken);
        Optional<Answer> latestAnswer = answerCheckService.getLatestAnswerByUserId(user.getId());
        Question question = questionCheckService.getUserQuestion(user, latestAnswer, level);
        Long answerId = answerService.createAnswer(user, question, latestAnswer, level);
        return new QuestionAnswerIdDto(question, answerId);
    }
}
