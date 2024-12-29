package Team02.BackEnd.service.user;

import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.recordDto.RecordRequestDto.GetVoiceUrlDto;
import Team02.BackEnd.repository.UserRepository;
import Team02.BackEnd.service.answer.AnswerService;
import Team02.BackEnd.service.feedback.FeedbackService;
import Team02.BackEnd.service.insight.InsightService;
import Team02.BackEnd.service.selffeedback.SelfFeedbackService;
import Team02.BackEnd.service.statistics.StatisticsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserCheckService userCheckService;
    private final UserRepository userRepository;

    @org.springframework.transaction.annotation.Transactional
    public void signOut(final String accessToken) {
        log.info("회원 탈퇴");
    }

    @org.springframework.transaction.annotation.Transactional
    public void updateRoleAndVoiceUrl(final String accessToken, final GetVoiceUrlDto getVoiceUrlDto) {
        User user = userCheckService.getUserByToken(accessToken);
        user.updateRole();
        user.updateVoiceUrl(getVoiceUrlDto.getVoiceUrl());
        userRepository.save(user);
        log.info("사용자의 목소리 저장, userId : {}", user.getId());
    }
}
