package Team02.BackEnd.service.feedback;

import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.dto.feedbackDto.FeedbackApiDataDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import Team02.BackEnd.dto.recordDto.RecordRequestDto.GetRespondDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackManager {

    private final FeedbackService feedbackService;
    private final FeedbackApiService feedbackApiService;
    private final FeedbackCheckService feedbackCheckService;

    public void saveBeforeAudioLink(final String accessToken, final GetRespondDto getRespondDto) {
        feedbackService.saveBeforeAudioLink(accessToken, getRespondDto);
    }

    public void createFeedbackData(final String accessToken, final Long answerId) {
        FeedbackApiDataDto feedbackApiDataDto = feedbackCheckService.getDataForFeedbackApi(accessToken, answerId);
        GetFeedbackToFastApiDto getFeedbackToFastApiDto = feedbackApiService.getFeedbackFromFastApi(accessToken,
                feedbackApiDataDto.beforeAudioLink(),
                feedbackApiDataDto.pastAudioLinks(), feedbackApiDataDto.userData(), answerId);
        feedbackService.updateFeedbackData(feedbackApiDataDto.feedback(), getFeedbackToFastApiDto);
    }

    public Feedback getFeedbackByAnswerId(final Long answerId) {
        return feedbackCheckService.getFeedbackByAnswerId(answerId);
    }

    public boolean doSpeechToday(final String accessToken) {
        return feedbackCheckService.doSpeechToday(accessToken);
    }
}
