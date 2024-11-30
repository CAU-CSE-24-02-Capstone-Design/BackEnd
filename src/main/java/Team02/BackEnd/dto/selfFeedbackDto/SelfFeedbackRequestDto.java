package Team02.BackEnd.dto.selfFeedbackDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SelfFeedbackRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveSelfFeedbackDto {
        String feedback;
    }
}
