package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.AnswerResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerConverter {
    public static Answer toAnswer(final User user, final Question question) {
        return Answer.builder()
                .question(question)
                .user(user)
                .evaluation(0)
                .build();
    }

    public static AnswerResponseDto.AnswerExistsDto toAnswerExistsDto(final Boolean isAnswerExists) {
        return AnswerResponseDto.AnswerExistsDto.builder()
                .isAnswerExists(isAnswerExists)
                .build();
    }

    public static AnswerResponseDto.AnswerEvaluationResponseDto toAnswerEvaluationResponseDto(final int evaluation) {
        return AnswerResponseDto.AnswerEvaluationResponseDto.builder()
                .evaluation(evaluation)
                .build();
    }
}
