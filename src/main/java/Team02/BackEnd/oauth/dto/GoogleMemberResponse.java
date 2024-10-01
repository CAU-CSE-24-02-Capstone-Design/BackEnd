package Team02.BackEnd.oauth.dto;

import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.OauthUser;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static Team02.BackEnd.oauth.OauthServerType.GOOGLE;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleMemberResponse(
        String id,
        String email,
        boolean verified_email,
        String name,
        String given_name,
        String family_name,
        String picture,
        String locale
) {

    public OauthUser toDomain() {
        return OauthUser.builder()
                .oauthId(new OauthId(id, GOOGLE))
                .email(email)
                .name(name)
//                .profileImageUrl(picture)
                .role(Role.GUEST)
                .build();
    }
}
