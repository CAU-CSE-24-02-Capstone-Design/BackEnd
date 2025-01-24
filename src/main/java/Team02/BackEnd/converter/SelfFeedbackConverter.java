package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.dto.selfFeedbackDto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import Team02.BackEnd.dto.selfFeedbackDto.SelfFeedbackResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelfFeedbackConverter {
    public static SelfFeedbackResponseDto.getBeforeSelfFeedbackDto toGetBeforeSelfFeedbackDto(
            final String selfFeedbackText) {
        return SelfFeedbackResponseDto.getBeforeSelfFeedbackDto.builder()
                .feedback(selfFeedbackText)
                .build();

    }

    public static SelfFeedback toSelfFeedback(final Answer answer, final SaveSelfFeedbackDto saveSelfFeedbackDto) {
        return SelfFeedback.builder()
                .answer(answer)
                .feedback(saveSelfFeedbackDto.getFeedback())
                .build();
    }

}
