package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.SelfFeedbackHandler;
import Team02.BackEnd.converter.SelfFeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
        if (isExistsSelfFeedback(answerId)) {
            SelfFeedback selfFeedback = getSelfFeedbackByAnswerId(answerId);
            selfFeedback.updateFeedback(saveSelfFeedbackDto.getFeedback());
            selfFeedbackRepository.save(selfFeedback);
            return;
        }
        SelfFeedback selfFeedback = SelfFeedbackConverter.toSelfFeedback(answer, saveSelfFeedbackDto);
        selfFeedbackRepository.save(selfFeedback);
    }

    public SelfFeedback getLatestSelfFeedback(final String accessToken) {
        User user = userService.getUserByToken(accessToken);
        List<Answer> answers = answerService.getAnswersByUserId(user.getId());
        SelfFeedback selfFeedback = answers.stream()
                .map(answer -> selfFeedbackRepository.findByAnswerId(answer.getId()))
                .filter(Objects::nonNull)
                .max(Comparator.comparing(SelfFeedback::getCreatedAt))
                .orElse(null);
        validateSelfFeedbackIsNotNull(selfFeedback);
        return selfFeedback;
    }

    public SelfFeedback getSelfFeedbackByAnswerId(final Long answerId) {
        return selfFeedbackRepository.findByAnswerId(answerId);
    }

    public boolean isExistsSelfFeedback(final Long answerId) {
        return selfFeedbackRepository.findByAnswerId(answerId) != null;
    }

    private void validateSelfFeedbackIsNotNull(final SelfFeedback selfFeedback) {
        if (selfFeedback == null) {
            throw new SelfFeedbackHandler(ErrorStatus._SELF_FEEDBACK_NOT_FOUND);
        }
    }
}

