package Team02.BackEnd.service;

import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.dto.FeedbackRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    public void saveFeedback(FeedbackRequestDto.GetFeedbackDto request) {

    }

    public Feedback getFeedback(Long answerId) {
        return null;
    }
}
