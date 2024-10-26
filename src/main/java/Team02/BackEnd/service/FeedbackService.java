package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.FeedbackHandler;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.dto.FeedbackRequestDto;
import Team02.BackEnd.dto.RecordRequestDto;
import Team02.BackEnd.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public void saveFeedback(FeedbackRequestDto.GetFeedbackDto request) {

    }

    public Feedback getFeedback(Long answerId) {
        return null;
    }

    public void setBeforeAudioLink(RecordRequestDto.GetRespondDto getRespondDto) {
        Feedback feedback = feedbackRepository.findByAnswerId(getRespondDto.getAnswerId()).orElse(null);
        if (feedback == null)
            throw new FeedbackHandler(ErrorStatus._FEEDBACK_NOT_FOUND);
        feedback.updateBeforeAudioLink(getRespondDto.getBeforeAudioLink());
    }
}
