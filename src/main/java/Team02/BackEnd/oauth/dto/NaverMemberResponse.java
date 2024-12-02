package Team02.BackEnd.oauth.dto;


import static Team02.BackEnd.oauth.OauthServerType.NAVER;

import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record NaverMemberResponse(
        String resultcode,
        String message,
        Response response
) {

    public User toDomain() {
        System.out.println(response.name);
        return User.builder()
                .oauthId(new OauthId(String.valueOf(response.id), NAVER))
                .name(response.name)
                .email(response.email)
                .level1QuestionNumber(1L)
                .level2QuestionNumber(1L)
                .level3QuestionNumber(1L)
                .analyzeCompleteAnswerIndex(0L)
                .role(Role.GUEST)
                .build();
    }

    public LocalDate getBirthDate(String birthyear, String birthday) {
        if (birthyear != null && birthday != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(birthyear + "-" + birthday, formatter);
        }
        return null;
    }

    public Integer calculateAge(String birthyear) {
        if (birthyear != null && !birthyear.isEmpty()) {
            try {
                int birthYear = Integer.parseInt(birthyear);
                int currentYear = Year.now().getValue();
                return currentYear - birthYear + 1;
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Response(
            String id,
            String nickname,
            String name,
            String email,
            String gender,
            String age,
            String birthday,
            String profileImage,
            String birthyear,
            String mobile
    ) {
    }
}
