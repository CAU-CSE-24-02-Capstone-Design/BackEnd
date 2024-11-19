package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.dto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import Team02.BackEnd.dto.SelfFeedbackResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelfFeedbackConverter {
    public static SelfFeedbackResponseDto.getBeforeSelfFeedbackDto toGetBeforeSelfFeedbackDto(
            final SelfFeedback selfFeedback) {
        return SelfFeedbackResponseDto.getBeforeSelfFeedbackDto.builder()
                .feedback(selfFeedback.getFeedback())
                .build();

    }

    public static SelfFeedback toSelfFeedback(final Answer answer, final SaveSelfFeedbackDto saveSelfFeedbackDto) {
        return SelfFeedback.builder()
                .answer(answer)
                .feedback(saveSelfFeedbackDto.getFeedback())
                .build();
    }

    public static SelfFeedbackResponseDto.getSelfFeedbackEvaluationDto toGetSelfFeedbackEvaluationDto(
            final int evaluation) {
        return SelfFeedbackResponseDto.getSelfFeedbackEvaluationDto.builder()
                .evaluation(evaluation)
                .build();

    }
}
