package Team02.BackEnd.dto.answerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AnswerResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnswerExistsDto {
        boolean isAnswerExists;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnswerEvaluationResponseDto {
        int evaluation;
    }
}
