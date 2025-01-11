package Team02.BackEnd.service.feedback;

import static Team02.BackEnd.constant.Constants.BASE_TIME_ZONE;
import static Team02.BackEnd.constant.Constants.NEW_TIME_ZONE;

import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.feedbackDto.FeedbackApiDataDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackDto.FeedbackAudioLinkDto;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import Team02.BackEnd.repository.FeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import Team02.BackEnd.validator.FeedbackValidator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public class FeedbackCheckService {

    private static final int LIMIT_PAST_AUDIO_NUMBER = 5;

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackValidator feedbackValidator;

    public boolean doSpeechToday(final String accessToken) {
        Long userId = userCheckService.getUserIdByToken(accessToken);
        log.info("오늘 스피치를 진행했는지 확인하기, userId : {}", userId);
        return answerCheckService.getAnswerIdsByUserId(userId).stream()
                .filter(this::isFeedbackExistsWithAnswerId)
                .map(this::getFeedbackCreatedAtByAnswerId)
                .anyMatch(date -> date.atZone(ZoneId.of(BASE_TIME_ZONE))
                        .withZoneSameInstant(ZoneId.of(NEW_TIME_ZONE)).toLocalDate()
                        .equals(LocalDate.now(ZoneId.of(NEW_TIME_ZONE))));
    }

    public List<String> getPastAudioLinks(final Long userId) {
        Pageable pageable = PageRequest.of(0, LIMIT_PAST_AUDIO_NUMBER);
        List<FeedbackDto.FeedbackAudioLinkDto> feedbackList = feedbackRepository.findBeforeLinksByUserId(userId,
                pageable);
        if (feedbackList.isEmpty()) {
            feedbackList = feedbackRepository.findAllBeforeLinksByUserId(userId);
        }
        return feedbackList.stream()
                .map(FeedbackAudioLinkDto::getBeforeAudioLink)
                .toList();
    }

    public FeedbackApiDataDto getFeedbackApiData(final String accessToken, final Long answerId) {
        Feedback feedback = this.getFeedbackByAnswerId(answerId);
        UserVoiceDto userData = userCheckService.getUserDataByToken(accessToken);
        String beforeAudioLink = feedback.getBeforeAudioLink();
        List<String> pastAudioLinks = feedbackValidator.validatePastAudioLinksIsNotIncludeToNull(
                this.getPastAudioLinks(userData.getId()));  // MAX 5개, 5개 이하면 다 가져온다
        return new FeedbackApiDataDto(feedback, beforeAudioLink, pastAudioLinks, userData);
    }

    public List<String> findBeforeScriptByUser(final User user, int number) {
        Pageable pageable = PageRequest.of(0, number);
        return feedbackRepository.findBeforeScriptByUserId(user.getId(), pageable);
    }

    public boolean isFeedbackExistsWithAnswerId(final Long answerId) {
        return feedbackRepository.existsByAnswerId(answerId);
    }

    public Feedback getFeedbackByAnswerId(final Long answerId) {
        Feedback feedback = feedbackRepository.findByAnswerId(answerId).orElse(null);
        feedbackValidator.validateFeedback(feedback);
        return feedback;
    }

    public LocalDateTime getFeedbackCreatedAtByAnswerId(final Long answerId) {
        LocalDateTime feedbackCreatedAt = feedbackRepository.findCreatedAtByAnswerId(answerId);
        feedbackValidator.validateFeedback(feedbackCreatedAt);
        return feedbackCreatedAt;
    }
}
