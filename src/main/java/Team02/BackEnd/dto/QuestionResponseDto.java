package Team02.BackEnd.dto;

import lombok.Getter;

public class QuestionResponseDto {

    @Getter
    public static class GetQuestionDto {
        String questionDescription;
        Long answerId;
    }
}
