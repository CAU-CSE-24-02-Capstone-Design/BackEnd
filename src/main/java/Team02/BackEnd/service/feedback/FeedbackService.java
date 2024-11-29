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
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {

    private final FeedbackApiService feedbackApiService;
    private final FeedbackCheckService feedbackCheckService;
    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final FeedbackRepository feedbackRepository;

    public void createFeedbackData(final String accessToken, final Long answerId) {
        Feedback feedback = feedbackCheckService.getFeedbackByAnswerId(answerId);
        User user = userCheckService.getUserByToken(accessToken);
        String beforeAudioLink = feedback.getBeforeAudioLink();
        List<String> pastAudioLinks = feedbackCheckService.getPastAudioLinks(user);  // MAX 5개, 5개 이하면 다 가져옴

        GetFeedbackToFastApiDto response = feedbackApiService.getFeedbackFromFastApi(accessToken,
                beforeAudioLink, pastAudioLinks, user, answerId);
        feedback.updateFeedbackData(response);
        feedbackRepository.save(feedback);
        log.info("스피치 분석 데이터 생성, feedbackId : {}", feedback.getId());
    }

    public void saveBeforeAudioLink(final String accessToken, final GetRespondDto getRespondDto) {
        User user = userCheckService.getUserByToken(accessToken);
        Answer answer = answerCheckService.getAnswerByAnswerId(getRespondDto.getAnswerId());
        Feedback feedback = FeedbackConverter.toFeedback(getRespondDto.getBeforeAudioLink(), answer, user);

        feedbackRepository.save(feedback);
        log.info("피드백 받기 전 스피치 URL 저장, feedbackId : {}", feedback.getId());
    }

}
