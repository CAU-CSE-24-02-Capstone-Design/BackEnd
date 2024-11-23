package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.SelfFeedbackHandler;
import Team02.BackEnd.converter.SelfFeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.selfFeedbackDto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
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
        log.info("스피치에 대한 셀프 피드백 저장, selfFeedbackId : {}", selfFeedback.getId());
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
        log.info("가장 최근 셀프 피드백 가져오기, selfFeedbackId : {}", selfFeedback.getId());
        return selfFeedback;
    }

    private SelfFeedback getSelfFeedbackByAnswerId(final Long answerId) {
        return selfFeedbackRepository.findByAnswerId(answerId);
    }

    private boolean isExistsSelfFeedback(final Long answerId) {
        return selfFeedbackRepository.findByAnswerId(answerId) != null;
    }

    private void validateSelfFeedbackIsNotNull(final SelfFeedback selfFeedback) {
        if (selfFeedback == null) {
            throw new SelfFeedbackHandler(ErrorStatus._SELF_FEEDBACK_NOT_FOUND);
        }
    }
}

