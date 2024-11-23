package Team02.BackEnd.oauth.service;

import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.oauth.AuthCodeRequestUrlProviderComposite;
import Team02.BackEnd.oauth.OauthServerType;
import Team02.BackEnd.oauth.client.OauthUserClientComposite;
import Team02.BackEnd.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
/**
 * OauthServerType을 받아서 해당 인증 서버에서 Auth Code를 받아오기 위한 URL 주소 생성
 * 로그인
 */

public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthUserClientComposite oauthUserClientComposite;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    public User login(HttpServletResponse response, OauthServerType oauthServerType, String authCode) {
        User user = oauthUserClientComposite.fetch(oauthServerType, authCode);
        User saved = userRepository.findByOauthId(user.getOauthId()).orElseGet(() -> userRepository.save(user));

        String accessToken = jwtService.createAccessToken(
                user.getEmail()); // JwtService의 createAccessToken을 사용하여 AccessToken 발급
        String refreshToken = jwtService.createRefreshToken(); // JwtService의 createRefreshToken을 사용하여 RefreshToken 발급

        jwtService.sendAccessAndRefreshToken(response, accessToken,
                refreshToken); // 응답 헤더에 AccessToken, 응답 쿠키에 RefreshToken 실어서 응답
        jwtService.updateRefreshToken(user.getEmail(), refreshToken); // DB에 RefreshToken 저장

        log.info("사용자 로그인 완료, email : {}", user.getEmail());
        return saved;
    }
}
