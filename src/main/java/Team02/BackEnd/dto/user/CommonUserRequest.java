package Team02.BackEnd.dto.user;

import lombok.*;

public class CommonUserRequest {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommonUserSignUpRequestDTO {
        private String email;
        private String password;
        private String name;
    }
}
