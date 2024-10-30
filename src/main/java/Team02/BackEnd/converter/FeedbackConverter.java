package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.dto.FeedbackResponseDto.GetFeedbackDto;
import Team02.BackEnd.dto.RecordRequestDto.GetRespondDto;

public class FeedbackConverter {
    public static GetFeedbackDto toGetFeedbackDto(Feedback feedback) {
        return GetFeedbackDto.builder()
                .beforeScript(feedback.getBeforeScript())
                .beforeAudioLink(feedback.getBeforeAudioLink())
                .afterScript(feedback.getAfterScript())
                .afterAudioLink(feedback.getAfterAudioLink())
                .feedbackText(feedback.getFeedbackText())
                .build();
    }

    public static Feedback toFeedback(String beforeAudioLink, Answer answer) {
        return Feedback.builder()
                .beforeAudioLink(beforeAudioLink)
                .answer(answer)
                .build();
    }
}
