package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.dto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import Team02.BackEnd.dto.SelfFeedbackResponseDto;

public class SelfFeedbackConverter {
    public static SelfFeedbackResponseDto.getBeforeSelfFeedbackDto toGetBeforeSelfFeedbackDto(
            SelfFeedback selfFeedback) {
        return SelfFeedbackResponseDto.getBeforeSelfFeedbackDto.builder()
                .feedback(selfFeedback.getFeedback())
                .build();

    }

    public static SelfFeedback toSelfFeedback(Answer answer, SaveSelfFeedbackDto saveSelfFeedbackDto) {
        return SelfFeedback.builder()
                .answer(answer)
                .feedback(saveSelfFeedbackDto.getFeedback())
                .build();
    }
}
