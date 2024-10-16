package Team02.BackEnd.oauth.kakao;

import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
import Team02.BackEnd.oauth.client.KakaoApiClient;
import Team02.BackEnd.oauth.client.OauthUserClient;
import Team02.BackEnd.oauth.dto.KakaoMemberResponse;
import Team02.BackEnd.oauth.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor

/**
 * fetch 메소드에 대한 설명
 * (1) - authCode를 통해 token을 가져온다
 * (2) - token을 통해 회원 정보를 받아온다
 * (3) - 회원정보를 OauthUser 객체로 변환한다.
 */

public class KakaoUserClient implements OauthUserClient {

    private final KakaoApiClient kakaoApiClient;
    private final KakaoOauthConfig kakaoOauthConfig;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.KAKAO;
    }

    @Override
    public User fetch(String authCode) {
        KakaoToken tokenInfo = kakaoApiClient.fetchToken(tokenRequestParams(authCode)); // (1)
        KakaoMemberResponse kakaoMemberResponse = kakaoApiClient.fetchMember(
                "Bearer " + tokenInfo.accessToken());  // (2)
        return kakaoMemberResponse.toDomain();  // (3)
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoOauthConfig.clientId());
        params.add("redirect_uri", kakaoOauthConfig.redirectUri());
        params.add("code", authCode);
        params.add("client_secret", kakaoOauthConfig.clientSecret());
        return params;
    }
}
