package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.dto.FeedbackRequestDto;
import Team02.BackEnd.dto.FeedbackRequestDto.GetComponentToMakeFeedback;
import Team02.BackEnd.dto.FeedbackResponseDto;
import Team02.BackEnd.dto.FeedbackResponseDto.GetFeedbackDto;
import java.util.List;

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

    public static FeedbackRequestDto.GetComponentToMakeFeedback toGetComponentToMakeFeedback(String beforeAudioLink, String name,
                                                                                              String voiceUrl,
                                                                                              List<String> pastAudioLinks) {
        return FeedbackRequestDto.GetComponentToMakeFeedback
                .builder()
                .beforeAudioLink(beforeAudioLink)
                .name(name)
                .voiceUrl(voiceUrl)
                .pastAudioLinks(pastAudioLinks)
                .build();
    }
}
