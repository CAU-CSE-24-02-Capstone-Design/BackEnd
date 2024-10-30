package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Question;
import Team02.BackEnd.dto.QuestionResponseDto;

public class QuestionConverter {
    public static QuestionResponseDto.GetQuestionDto toQuestionResponseDto(Question question, Long answerId) {
        return QuestionResponseDto.GetQuestionDto.builder()
                .questionDescription(question.getDescription())
                .answerId(answerId)
                .build();
    }
}
