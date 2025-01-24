package Team02.BackEnd.service.selffeedback;

import Team02.BackEnd.dto.selfFeedbackDto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SelfFeedbackManager {

    private final SelfFeedbackCheckService selfFeedbackCheckService;
    private final SelfFeedbackService selfFeedbackService;

    public void saveSelfFeedback(final Long answerId, final SaveSelfFeedbackDto saveSelfFeedbackDto) {
        selfFeedbackService.saveSelfFeedback(answerId, saveSelfFeedbackDto);
    }

    public String getLatestSelfFeedbackText(final String accessToken) {
        return selfFeedbackCheckService.getLatestSelfFeedbackText(accessToken);
    }
}
