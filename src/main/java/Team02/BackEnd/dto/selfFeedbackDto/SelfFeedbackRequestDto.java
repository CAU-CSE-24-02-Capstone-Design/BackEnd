package Team02.BackEnd.dto.selfFeedbackDto;

import lombok.Getter;

public class SelfFeedbackRequestDto {

    @Getter
    public static class SaveSelfFeedbackDto {
        String feedback;
    }
}
