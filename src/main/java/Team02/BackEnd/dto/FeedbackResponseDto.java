package Team02.BackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FeedbackResponseDto {

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
    }
}
