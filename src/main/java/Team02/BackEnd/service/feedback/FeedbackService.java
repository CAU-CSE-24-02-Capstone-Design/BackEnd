package Team02.BackEnd.service.feedback;

import Team02.BackEnd.converter.FeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import Team02.BackEnd.dto.recordDto.RecordRequestDto.GetRespondDto;
import Team02.BackEnd.repository.FeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED)
public class FeedbackService {

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final FeedbackRepository feedbackRepository;

    public void saveBeforeAudioLink(final String accessToken, final GetRespondDto getRespondDto) {
        User user = userCheckService.getUserByToken(accessToken);
        Answer answer = answerCheckService.getAnswerByAnswerId(getRespondDto.getAnswerId());
        Feedback feedback = FeedbackConverter.toFeedback(getRespondDto.getBeforeAudioLink(), answer, user);
        feedbackRepository.save(feedback);
        log.info("피드백 받기 전 스피치 URL 저장, feedbackId : {}", feedback.getId());
    }

    public void updateFeedbackData(final Feedback feedback, final GetFeedbackToFastApiDto response) {
        feedback.updateFeedbackData(response);
        log.info("스피치 분석 데이터 생성, feedbackId : {}", feedback.getId());
    }
}
