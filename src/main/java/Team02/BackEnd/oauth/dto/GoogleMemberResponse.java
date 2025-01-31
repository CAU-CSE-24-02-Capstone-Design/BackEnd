package Team02.BackEnd.oauth.dto;

import static Team02.BackEnd.oauth.OauthServerType.GOOGLE;

import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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

    public User toDomain() {
        System.out.println(name);
        return User.builder()
                .oauthId(new OauthId(id, GOOGLE))
                .email(email)
                .name(name)
                .level1QuestionNumber(1L)
                .level2QuestionNumber(1L)
                .level3QuestionNumber(1L)
                .analyzeCompleteAnswerIndex(0L)
                .sequenceCount(0)
                .score(0)
                .role(Role.GUEST)
                .build();
    }
}
