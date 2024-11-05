package Team02.BackEnd.dto;

import lombok.Getter;

public class SelfFeedbackRequestDto {

    @Getter
    public static class SaveSelfFeedbackDto {
        String good;
        String bad;
    }
}
