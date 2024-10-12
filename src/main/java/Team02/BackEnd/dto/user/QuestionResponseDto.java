package Team02.BackEnd.dto.user;

import lombok.Getter;

public class QuestionResponseDto {

    @Getter
    public static class GetQuestionDto {
        String questionDescription;
        Long answerId;
    }
}
