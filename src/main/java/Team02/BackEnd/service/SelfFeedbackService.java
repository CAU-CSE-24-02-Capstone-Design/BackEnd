package Team02.BackEnd.service;

import Team02.BackEnd.converter.SelfFeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.dto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelfFeedbackService {

    private final SelfFeedbackRepository selfFeedbackRepository;
    private final AnswerService answerService;

    public void saveSelfFeedback(Long answerId, SaveSelfFeedbackDto saveSelfFeedbackDto) {
        Answer answer = answerService.getAnswerByAnswerId(answerId);
        SelfFeedback selfFeedback = SelfFeedbackConverter.toSelfFeedback(answer, saveSelfFeedbackDto);
        selfFeedbackRepository.save(selfFeedback);
    }

    public SelfFeedback getBeforeSelfFeedbackByAnswer(Long answerId) {
        Answer answer = answerService.getAnswerByAnswerId(answerId);
        return selfFeedbackRepository.findByAnswerId(answer.getId());
    }
}
