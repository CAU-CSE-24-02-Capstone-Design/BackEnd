package Team02.BackEnd.service.selffeedback;

import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import Team02.BackEnd.validator.SelfFeedbackValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public class SelfFeedbackCheckService {

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final SelfFeedbackRepository selfFeedbackRepository;
    private final SelfFeedbackValidator selfFeedbackValidator;

    public SelfFeedback getLatestSelfFeedback(final String accessToken) {
        Long userId = userCheckService.getUserIdByToken(accessToken);
        Long answerId = answerCheckService.getAnswerIdsByUserIdWithSize(userId, 1).get(0);
        SelfFeedback selfFeedback = selfFeedbackRepository.findByAnswerId(answerId);
        selfFeedbackValidator.validateSelfFeedback(selfFeedback);
        log.info("가장 최근 셀프 피드백 가져오기, selfFeedbackId : {}", selfFeedback.getId());
        return selfFeedback;
    }

    public SelfFeedback getSelfFeedbackByAnswerId(final Long answerId) {
        return selfFeedbackRepository.findByAnswerId(answerId);
    }

    public boolean isExistsSelfFeedbackWithAnswerId(final Long answerId) {
        return selfFeedbackRepository.existsByAnswerId(answerId);
    }
}
