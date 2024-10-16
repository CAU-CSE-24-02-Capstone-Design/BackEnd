package Team02.BackEnd.oauth.client;

import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;

/**
 * AuthCode를 통해 OauthMember 객체 생성 (회원 정보 조회)
 */

public interface OauthUserClient {

    OauthServerType supportServer();

    User fetch(String code);
}