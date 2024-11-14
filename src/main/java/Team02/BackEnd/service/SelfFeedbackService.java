package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.SelfFeedbackHandler;
import Team02.BackEnd.converter.SelfFeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelfFeedbackService {

    private final SelfFeedbackRepository selfFeedbackRepository;
    private final UserService userService;
    private final AnswerService answerService;

    public void saveSelfFeedback(final Long answerId, final SaveSelfFeedbackDto saveSelfFeedbackDto) {
        Answer answer = answerService.getAnswerByAnswerId(answerId);
        SelfFeedback selfFeedback = SelfFeedbackConverter.toSelfFeedback(answer, saveSelfFeedbackDto);
        selfFeedbackRepository.save(selfFeedback);
    }

    public SelfFeedback getLatestSelfFeedback(final String accessToken) {
        User user = userService.getUserByToken(accessToken);
        Answer answer = answerService.getLatestAnswerByUserId(user.getId());
        return getSelfFeedbackByAnswerId(answer.getId());
    }

    public SelfFeedback getSelfFeedbackByAnswerId(final Long answerId) {
        SelfFeedback selfFeedback = selfFeedbackRepository.findByAnswerId(answerId);
        validateSelfFeedbackIsNotNull(selfFeedback);
        return selfFeedback;
    }

    private void validateSelfFeedbackIsNotNull(final SelfFeedback selfFeedback) {
        if (selfFeedback == null) {
            throw new SelfFeedbackHandler(ErrorStatus._SELF_FEEDBACK_NOT_FOUND);
        }
    }
}
