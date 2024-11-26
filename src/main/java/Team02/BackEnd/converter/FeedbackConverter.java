package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.feedbackDto.FeedbackRequestDto.GetComponentToMakeFeedbackDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackConverter {
    public static GetFeedbackDto toGetFeedbackDto(final Feedback feedback) {
        return GetFeedbackDto.builder()
                .beforeScript(feedback.getBeforeScript())
                .beforeAudioLink(feedback.getBeforeAudioLink())
                .afterScript(feedback.getAfterScript())
                .afterAudioLink(feedback.getAfterAudioLink())
                .feedbackText(feedback.getFeedbackText())
                .build();
    }

    public static Feedback toFeedback(final String beforeAudioLink, final Answer answer, final User user) {
        return Feedback.builder()
                .beforeAudioLink(beforeAudioLink)
                .answer(answer)
                .user(user)
                .build();
    }

    public static GetComponentToMakeFeedbackDto toGetComponentToMakeFeedback(final String beforeAudioLink,
                                                                             final User user,
                                                                             final List<String> pastAudioLinks,
                                                                             final Long answerId) {
        return GetComponentToMakeFeedbackDto
                .builder()
                .beforeAudioLink(beforeAudioLink)
                .name(user.getName())
                .voiceUrl(user.getVoiceUrl())
                .pastAudioLinks(pastAudioLinks)
                .answerId(answerId)
                .build();
    }

    public static FeedbackResponseDto.SpeechExistsDto toGetSpeechExistsDto(final Boolean isSpeechExists) {
        return FeedbackResponseDto.SpeechExistsDto.builder()
                .isSpeechExists(isSpeechExists)
                .build();
    }
}
