package Team02.BackEnd.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserFamilyInfoRequestDTO {
        String isParent;
        String familyCode;
    }
}
