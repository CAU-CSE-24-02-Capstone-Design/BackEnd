package Team02.BackEnd.service.oauth;

import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.oauth.AuthCodeRequestUrlProviderComposite;
import Team02.BackEnd.oauth.OauthServerType;
import Team02.BackEnd.oauth.client.OauthUserClientComposite;
import Team02.BackEnd.oauth.service.OauthService;
import Team02.BackEnd.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OauthServiceTest {

    @Mock
    private AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    @Mock
    private OauthUserClientComposite oauthUserClientComposite;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private OauthService oauthService;

    private HttpServletResponse response;
    private OauthServerType oauthServerType;
    private String authCode;
    private User user;

    @BeforeEach
    void setUp() {
        oauthServerType = OauthServerType.GOOGLE;
        authCode = "AuthCode";
        user = createUser();
    }

    @DisplayName("AuthCode 받아오는 Url 생성")
    @Test
    void getAuthCodeRequestUrl() {
        // given
        String requestUrl = "url";

        // when
        given(authCodeRequestUrlProviderComposite.provide(oauthServerType)).willReturn(requestUrl);
        String authCodeRequestUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);

        // then
        assertThat(authCodeRequestUrl).isEqualTo(requestUrl);
    }

    @DisplayName("소셜 로그인을 한다")
    @Test
    void login() {
        // given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        // when
        given(oauthUserClientComposite.fetch(oauthServerType, authCode)).willReturn(user);
        given(userRepository.findByOauthId(any(OauthId.class))).willReturn(Optional.of(user));
        given(jwtService.createAccessToken(user.getEmail())).willReturn(accessToken);
        given(jwtService.createRefreshToken()).willReturn(refreshToken);

        User loginUser = oauthService.login(response, oauthServerType, authCode);

        // then
        verify(jwtService, times(1)).sendAccessAndRefreshToken(response, accessToken, refreshToken);
        verify(jwtService, times(1)).updateRefreshToken(user.getEmail(), refreshToken);
        assertThat(loginUser).isNotNull();
        assertThat(loginUser.getEmail()).isEqualTo(user.getEmail());
    }
}