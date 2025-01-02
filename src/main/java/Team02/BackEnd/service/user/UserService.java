package Team02.BackEnd.service.user;

import static Team02.BackEnd.oauth.OauthServerType.GOOGLE;

import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.recordDto.RecordRequestDto.GetVoiceUrlDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.UserRepository;
import Team02.BackEnd.service.answer.AnswerService;
import Team02.BackEnd.service.feedback.FeedbackService;
import Team02.BackEnd.service.insight.InsightService;
import Team02.BackEnd.service.selffeedback.SelfFeedbackService;
import Team02.BackEnd.service.statistics.StatisticsService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserCheckService userCheckService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    // 부하테스트용 회원가입 로직
    @Transactional
    public String signUp(final HttpServletResponse response){
        String randomName = "User_" + UUID.randomUUID().toString().substring(0, 8);
        String randomEmail = "user" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";

        User user = User.builder()
                .oauthId(new OauthId(UUID.randomUUID().toString(), GOOGLE))
                .name(randomName)
                .email(randomEmail)
                .level1QuestionNumber(1L)
                .level2QuestionNumber(1L)
                .level3QuestionNumber(1L)
                .analyzeCompleteAnswerIndex(0L)
                .role(Role.USER)
                .build();
        String accessToken = jwtService.createAccessToken(user.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(user.getEmail(), refreshToken);

        userRepository.save(user);
        return accessToken;
    }

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
