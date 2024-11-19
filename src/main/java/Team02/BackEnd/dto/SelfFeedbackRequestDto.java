package Team02.BackEnd.dto;

import lombok.Getter;

public class SelfFeedbackRequestDto {

    @Getter
    public static class SaveSelfFeedbackDto {
        String feedback;
    }

    @Getter
    public static class SaveSelfFeedbackEvaluationDto {
        int evaluation;
    }
}
