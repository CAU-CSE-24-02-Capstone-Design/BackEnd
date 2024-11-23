package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnswerHandler;
import Team02.BackEnd.converter.AnswerConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnswerRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AnswerService {

    private static final String BASE_TIME_ZONE = "UTC";
    private static final String NEW_TIME_ZONE = "Asia/Seoul";

    private final AnswerRepository answerRepository;
    private final UserService userService;

    public Long getAnswerId(final String accessToken, final Question question) {
        User user = userService.getUserByToken(accessToken);
        Answer answer = AnswerConverter.toAnswer(user, question);
        answerRepository.save(answer);
        log.info("answer 엔티티 생성, answerId : {}", answer.getId());
        return answer.getId();
    }

    public List<Answer> getAnswersByUserId(final Long userId) {
        List<Answer> answers = answerRepository.findByUserId(userId);
        answers.forEach(this::validateAnswerIsNotNull);
        return answers;
    }

    public Answer getAnswerByAnswerId(final Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        validateAnswerIsNotNull(answer);
        return answer;
    }

    public boolean doAnswerToday(final String accessToken) {
        User user = userService.getUserByToken(accessToken);
        log.info("오늘 스피치를 진행했는지 확인하기, userId : {}", user.getId());
        return getAnswersByUserId(user.getId()).stream()
                .anyMatch(answer -> answer.getCreatedAt().atZone(ZoneId.of(BASE_TIME_ZONE))
                        .withZoneSameInstant(ZoneId.of(NEW_TIME_ZONE)).toLocalDate()
                        .equals(LocalDate.now(ZoneId.of(NEW_TIME_ZONE))));
    }

    public void saveAnswerEvaluation(final Long answerId, final int evaluation) {
        Answer answer = getAnswerByAnswerId(answerId);
        answer.updateEvaluation(evaluation);
        answerRepository.save(answer);
        log.info("스피치에 대한 평가 점수 저장, answerId : {}, 점수 : {}", answerId, evaluation);
    }

    public int getAnswerEvaluation(final Long answerId) {
        Answer answer = getAnswerByAnswerId(answerId);
        return answer.getEvaluation();
    }

    private void validateAnswerIsNotNull(final Answer answer) {
        if (answer == null) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
    }
}
