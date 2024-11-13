package Team02.BackEnd.service;

import Team02.BackEnd.converter.SelfFeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import Team02.BackEnd.exception.validator.SelfFeedbackValidator;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelfFeedbackService {

    private final SelfFeedbackRepository selfFeedbackRepository;
    private final UserService userService;
    private final AnswerService answerService;

    public void saveSelfFeedback(Long answerId, SaveSelfFeedbackDto saveSelfFeedbackDto) {
        Answer answer = answerService.getAnswerByAnswerId(answerId);
        SelfFeedback selfFeedback = SelfFeedbackConverter.toSelfFeedback(answer, saveSelfFeedbackDto);
        selfFeedbackRepository.save(selfFeedback);
    }

    public SelfFeedback getBeforeSelfFeedback(String accessToken) {
        User user = userService.getUserByToken(accessToken);
        Answer answer = answerService.getLatestAnswerByUserId(user.getId());
        return getSelfFeedbackByAnswerId(answer.getId());
    }

    public SelfFeedback getSelfFeedbackByAnswerId(Long answerId) {
        SelfFeedback selfFeedback = selfFeedbackRepository.findByAnswerId(answerId);
        SelfFeedbackValidator.validateSelfFeedbackIsNotNull(selfFeedback);
        return selfFeedback;
    }
}
