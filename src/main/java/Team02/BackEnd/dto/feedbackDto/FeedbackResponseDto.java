package Team02.BackEnd.dto.feedbackDto;

import jakarta.validation.constraints.NotNull;
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
        String beforeScript;
        String beforeAudioLink;
        String afterScript;
        String afterAudioLink;
        String feedbackText;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetFeedbackToFastApiDto {
        @NotNull
        String beforeScript;
        @NotNull
        String afterScript;
        @NotNull
        String afterAudioLink;
        @NotNull
        String feedbackText;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SpeechExistsDto {
        boolean isSpeechExists;
    }
}
