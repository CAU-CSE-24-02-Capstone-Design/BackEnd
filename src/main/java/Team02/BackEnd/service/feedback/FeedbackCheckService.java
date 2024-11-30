package Team02.BackEnd.service.feedback;

import static Team02.BackEnd.constant.Constants.BASE_TIME_ZONE;
import static Team02.BackEnd.constant.Constants.NEW_TIME_ZONE;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.FeedbackHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.FeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class FeedbackCheckService {

    private static final int LIMIT_PAST_AUDIO_NUMBER = 5;

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final FeedbackRepository feedbackRepository;

    public boolean doSpeechToday(final String accessToken) {
        User user = userCheckService.getUserByToken(accessToken);
        log.info("오늘 스피치를 진행했는지 확인하기, userId : {}", user.getId());
        return answerCheckService.getAnswersByUser(user).stream()
                .filter(this::isFeedbackExistsWithAnswer)
                .map(answer -> this.getFeedbackByAnswerId(answer.getId()))
                .anyMatch(feedback -> feedback.getCreatedAt().atZone(ZoneId.of(BASE_TIME_ZONE))
                        .withZoneSameInstant(ZoneId.of(NEW_TIME_ZONE)).toLocalDate()
                        .equals(LocalDate.now(ZoneId.of(NEW_TIME_ZONE))));
    }

    public List<String> getPastAudioLinks(final User user) {
        PageRequest pageRequest = PageRequest.of(0, LIMIT_PAST_AUDIO_NUMBER, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Feedback> feedbackPageList = feedbackRepository.findByUserId(user.getId(), pageRequest);

        List<Feedback> feedbackList = feedbackPageList.getContent();
        if (feedbackPageList.isEmpty()) {
            feedbackList = feedbackRepository.findAllByUserId(user.getId());
        }
        return feedbackList.stream()
                .map(Feedback::getBeforeAudioLink)
                .toList();
    }

    public List<String> findBeforeScriptByUser(User user, int number) {
        Pageable pageable = PageRequest.of(0, number, Sort.by("createdAt").descending());
        return feedbackRepository.findBeforeScriptByUser(user, pageable);
    }


    public boolean isFeedbackExistsWithAnswer(final Answer answer) {
        return feedbackRepository.findByAnswerId(answer.getId()).isPresent();
    }

    public Feedback getFeedbackByAnswerId(final Long answerId) {
        Feedback feedback = feedbackRepository.findByAnswerId(answerId).orElse(null);
        validateFeedbackIsNotNull(feedback);
        return feedback;
    }

    private void validateFeedbackIsNotNull(final Feedback feedback) {
        if (feedback == null) {
            throw new FeedbackHandler(ErrorStatus._FEEDBACK_NOT_FOUND);
        }
    }
}
