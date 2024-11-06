package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.FeedbackRequestDto.GetComponentToMakeFeedbackDto;
import Team02.BackEnd.dto.FeedbackResponseDto;
import Team02.BackEnd.dto.FeedbackResponseDto.GetFeedbackDto;
import Team02.BackEnd.dto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import java.util.List;

public class FeedbackConverter {
    public static GetFeedbackDto toGetFeedbackDto(GetFeedbackToFastApiDto getFeedbackDto) {
        return GetFeedbackDto.builder()
                .beforeScript(getFeedbackDto.getBeforeScript())
                .beforeAudio(getFeedbackDto.getBeforeAudio())
                .afterScript(getFeedbackDto.getAfterScript())
                .afterAudio(getFeedbackDto.getAfterAudio())
                .feedbackText(getFeedbackDto.getFeedbackText())
                .build();
    }

    public static Feedback toFeedback(String beforeAudioLink, Answer answer, User user) {
        return Feedback.builder()
                .beforeAudioLink(beforeAudioLink)
                .answer(answer)
                .user(user)
                .build();
    }

    public static GetComponentToMakeFeedbackDto toGetComponentToMakeFeedback(String beforeAudioLink, String name,
                                                                             String voiceUrl,
                                                                             List<String> pastAudioLinks,
                                                                             Long answerId) {
        return GetComponentToMakeFeedbackDto
                .builder()
                .beforeAudioLink(beforeAudioLink)
                .name(name)
                .voiceUrl(voiceUrl)
                .pastAudioLinks(pastAudioLinks)
                .answerId(answerId)
                .build();
    }
}
