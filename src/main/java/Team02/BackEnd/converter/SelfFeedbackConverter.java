package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.dto.SelfFeedbackRequestDto;
import Team02.BackEnd.dto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import Team02.BackEnd.dto.SelfFeedbackResponseDto;
import Team02.BackEnd.dto.SelfFeedbackResponseDto.getBeforeSelfFeedbackDto;

public class SelfFeedbackConverter {
    public static SelfFeedbackResponseDto.getBeforeSelfFeedbackDto toGetBeforeSelfFeedbackDto(SelfFeedback selfFeedback){
        return SelfFeedbackResponseDto.getBeforeSelfFeedbackDto.builder()
                .good(selfFeedback.getGood())
                .bad(selfFeedback.getBad())
                .build();

    }

    public static SelfFeedback toSelfFeedback(Answer answer, SaveSelfFeedbackDto saveSelfFeedbackDto) {
        return SelfFeedback.builder()
                .answer(answer)
                .good(saveSelfFeedbackDto.getGood())
                .bad(saveSelfFeedbackDto.getBad())
                .build();
    }
}
