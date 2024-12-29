package Team02.BackEnd.service.selffeedback;

import Team02.BackEnd.converter.SelfFeedbackConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.dto.selfFeedbackDto.SelfFeedbackRequestDto.SaveSelfFeedbackDto;
import Team02.BackEnd.repository.SelfFeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SelfFeedbackService {

    private final SelfFeedbackCheckService selfFeedbackCheckService;
    private final AnswerCheckService answerCheckService;
    private final SelfFeedbackRepository selfFeedbackRepository;

    @Transactional
    public void saveSelfFeedback(final Long answerId, final SaveSelfFeedbackDto saveSelfFeedbackDto) {
        Answer answer = answerCheckService.getAnswerByAnswerId(answerId);
        if (selfFeedbackCheckService.isExistsSelfFeedbackWithAnswerId(answerId)) {
            SelfFeedback selfFeedback = selfFeedbackCheckService.getSelfFeedbackByAnswerId(answerId);
            selfFeedback.updateFeedback(saveSelfFeedbackDto.getFeedback());
            log.info("스피치에 대한 셀프 피드백 업데이트, selfFeedbackId : {}", selfFeedback.getId());
            return;
        }
        SelfFeedback selfFeedback = SelfFeedbackConverter.toSelfFeedback(answer, saveSelfFeedbackDto);
        selfFeedbackRepository.save(selfFeedback);
        log.info("스피치에 대한 셀프 피드백 저장, selfFeedbackId : {}", selfFeedback.getId());
    }
}

