package Team02.BackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FeedbackRequestDto {

    @Getter
    public static class GetFeedbackDto{
        String beforeAudioLink;
        String beforeScript;
        String afterAudioLink;
        String afterScript;
        String feedbackText;

        Long answerId;
    }
}