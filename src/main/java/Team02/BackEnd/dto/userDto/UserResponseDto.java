package Team02.BackEnd.dto.userDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDto {
        String accessToken;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserScoreRankingDto {
        String email;
        String name;
        int score;
    }
}
