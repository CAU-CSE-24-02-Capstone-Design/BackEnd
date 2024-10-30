package Team02.BackEnd.converter;

import Team02.BackEnd.dto.QuestionResponseDto;

public class QuestionConverter {
    public static QuestionResponseDto.GetQuestionDto toQuestionResponseDto(String questionDescription, Long answerId) {
        return QuestionResponseDto.GetQuestionDto.builder()
                .questionDescription(questionDescription)
                .answerId(answerId)
                .build();
    }
}
