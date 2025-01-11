package Team02.BackEnd.dto.questionDto;

import Team02.BackEnd.domain.Question;

public record QuestionAnswerIdDto(Question question, Long answerId) {
}
