package Team02.BackEnd.dto;

import lombok.Getter;

public class FeedbackRequestDto {

    @Getter
    public static class GetFeedbackDto {
        String beforeAudioLink;
        String beforeScript;
        String afterAudioLink;
        String afterScript;
        String feedbackText;

        Long answerId;
    }
}
