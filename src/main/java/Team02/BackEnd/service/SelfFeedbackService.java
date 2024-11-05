package Team02.BackEnd.service;

import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.dto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelfFeedbackService {
    public void saveSelfFeedback(Long answerId, SaveSelfFeedbackDto saveSelfFeedbackDto) {

    }

    public SelfFeedback getBeforeSelfFeedback(Long answerId) {

        return null;
    }
}
