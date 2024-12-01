package Team02.BackEnd.oauth.dto;

import static Team02.BackEnd.oauth.OauthServerType.KAKAO;

import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;


/**
 * AccessToken을 사용해서 Kakao로부터 사용자의 정보를 받아오는 클래스
 */

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoMemberResponse(
        Long id,
        boolean hasSignedUp,
        LocalDateTime connectedAt,
        KakaoAccount kakaoAccount
) {

    public User toDomain() {
        System.out.println(kakaoAccount.profile.nickname);
        return User.builder()
                .oauthId(new OauthId(String.valueOf(id), KAKAO))
                .name(kakaoAccount.profile.nickname)
                .email(kakaoAccount.email)
                .level1QuestionNumber(1L)
                .level2QuestionNumber(1L)
                .level3QuestionNumber(1L)
                .analyzeCompleteAnswerIndex(0L)
                .role(Role.GUEST)
                .build();
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record KakaoAccount(
            boolean profileNeedsAgreement,
            boolean profileNicknameNeedsAgreement,
            boolean profileImageNeedsAgreement,
            Profile profile,
            boolean nameNeedsAgreement,
            String name,
            boolean emailNeedsAgreement,
            boolean isEmailValid,
            boolean isEmailVerified,
            String email,
            boolean ageRangeNeedsAgreement,
            String ageRange,
            boolean birthyearNeedsAgreement,
            String birthyear,
            boolean birthdayNeedsAgreement,
            String birthday,
            String birthdayType,
            boolean genderNeedsAgreement,
            String gender,
            boolean phoneNumberNeedsAgreement,
            String phoneNumber,
            boolean ciNeedsAgreement,
            String ci,
            LocalDateTime ciAuthenticatedAt
    ) {
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record Profile(
            String nickname,
            String thumbnailImageUrl,
            String profileImageUrl,
            boolean isDefaultImage
    ) {
    }
}
