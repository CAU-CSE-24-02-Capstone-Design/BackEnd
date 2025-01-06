package Team02.BackEnd.service.selffeedback;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.SelfFeedbackHandler;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class SelfFeedbackCheckService {

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final SelfFeedbackRepository selfFeedbackRepository;

    @Transactional(readOnly = true)
    public SelfFeedback getLatestSelfFeedback(final String accessToken) {
        Long userId = userCheckService.getUserIdByToken(accessToken);
        Long answerId = answerCheckService.getAnswerIdsByUserWithSize(userId, 1).get(0);
        SelfFeedback selfFeedback = selfFeedbackRepository.findByAnswerId(answerId);
        validateSelfFeedbackIsNotNull(selfFeedback);
        log.info("가장 최근 셀프 피드백 가져오기, selfFeedbackId : {}", selfFeedback.getId());
        return selfFeedback;
    }

    @Transactional(readOnly = true)
    public SelfFeedback getSelfFeedbackByAnswerId(final Long answerId) {
        return selfFeedbackRepository.findByAnswerId(answerId);
    }

    @Transactional(readOnly = true)
    public boolean isExistsSelfFeedbackWithAnswerId(final Long answerId) {
        return selfFeedbackRepository.existsByAnswerId(answerId);
    }

    private void validateSelfFeedbackIsNotNull(final SelfFeedback selfFeedback) {
        if (selfFeedback == null) {
            throw new SelfFeedbackHandler(ErrorStatus._SELF_FEEDBACK_NOT_FOUND);
        }
    }
}
