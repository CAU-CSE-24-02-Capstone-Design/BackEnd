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

    public String getLatestSelfFeedbackText(final String accessToken) {
        Long userId = userCheckService.getUserIdByToken(accessToken);
        Long answerId = answerCheckService.getAnswerIdsByUserIdWithSize(userId, 1).get(0);
        String selfFeedbackText = selfFeedbackRepository.findSelfFeedbackText(answerId).orElse(null);
        selfFeedbackValidator.validateSelfFeedback(selfFeedbackText);
        log.info("가장 최근 셀프 피드백 가져오기, userId : {}", userId);
        return selfFeedbackText;
    }

    public SelfFeedback getSelfFeedbackByAnswerId(final Long answerId) {
        return selfFeedbackRepository.findByAnswerId(answerId);
    }

    public boolean isExistsSelfFeedbackWithAnswerId(final Long answerId) {
        return selfFeedbackRepository.existsByAnswerId(answerId);
    }
}
