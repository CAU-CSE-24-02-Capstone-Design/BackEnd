package Team02.BackEnd.service.answer;

import Team02.BackEnd.converter.AnswerConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnswerRepository;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {

    private final AnswerCheckService answerCheckService;
    private final UserCheckService userCheckService;
    private final FeedbackCheckService feedbackCheckService;
    private final AnswerRepository answerRepository;

    @Transactional
    public Long createAnswer(final String accessToken, final Question question, final Long level) {
        User user = userCheckService.getUserByToken(accessToken);
        Optional<Answer> latestAnswer = answerCheckService.getLatestAnswerByUser(user);
        if (latestAnswer.isPresent() && !feedbackCheckService.isFeedbackExistsWithAnswer(latestAnswer.get())) {
            log.info("녹음이 진행 안 된 answer 엔티티 재사용, answerId : {}", latestAnswer.get().getId());
            return latestAnswer.get().getId();
        }
        Answer answer = answerRepository.saveAndFlush(AnswerConverter.toAnswer(user, question));
        user.addQuestionNumber(level);
        log.info("answer 엔티티 생성, answerId : {}", answer.getId());
        return answer.getId();
    }

    @Transactional
    public void saveAnswerEvaluation(final Long answerId, final int evaluation) {
        Answer answer = answerCheckService.getAnswerByAnswerId(answerId);
        answer.updateEvaluation(evaluation);
        log.info("스피치에 대한 평가 점수 저장, answerId : {}, 점수 : {}", answer.getId(), evaluation);
    }
}
