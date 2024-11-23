package Team02.BackEnd.dto.feedbackDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FeedbackRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetFeedbackDto {
        String beforeAudioLink;
        String beforeScript;
        String afterAudioLink;
        String afterScript;
        String feedbackText;
        Long answerId;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetComponentToMakeFeedbackDto {
        String beforeAudioLink;
        String name;
        String voiceUrl;
        List<String> pastAudioLinks;
        Long answerId;
    }
}
