package Team02.BackEnd.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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

}
