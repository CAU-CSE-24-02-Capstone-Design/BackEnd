package Team02.BackEnd.oauth;

import java.util.Locale;

public enum OauthServerType {

    KAKAO,
    NAVER,
    GOOGLE;

    public static OauthServerType fromName(final String type) {
        return OauthServerType.valueOf(type.toUpperCase(Locale.ENGLISH));
    }
}