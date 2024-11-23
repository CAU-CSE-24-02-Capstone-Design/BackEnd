package Team02.BackEnd.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserFamilyInfoRequestDto {
        String isParent;
        String familyCode;
    }
}
