package Team02.BackEnd.service;

import Team02.BackEnd.converter.SelfFeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.dto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import Team02.BackEnd.repository.AnswerRepository;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelfFeedbackService {

    private final SelfFeedbackRepository selfFeedbackRepository;

    public void saveSelfFeedback(Answer answer, SaveSelfFeedbackDto saveSelfFeedbackDto) {
        SelfFeedback selfFeedback = SelfFeedbackConverter.toSelfFeedback(answer, saveSelfFeedbackDto);
        selfFeedbackRepository.save(selfFeedback);
    }

    public SelfFeedback getBeforeSelfFeedback(Answer answer) {
        return selfFeedbackRepository.findByAnswerId(answer.getId());
    }
}
