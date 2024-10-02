package Team02.BackEnd.converter;

import Team02.BackEnd.domain.CommonUser;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.dto.user.CommonUserRequest;
import Team02.BackEnd.dto.user.CommonUserResponse;

public class CommonUserConverter {

    public static CommonUser toCommonUser(CommonUserRequest.CommonUserSignUpRequestDTO commonUserSignUpRequestDTO) {
        return CommonUser.builder()
                .email(commonUserSignUpRequestDTO.getEmail())
                .name(commonUserSignUpRequestDTO.getName())
                .password(commonUserSignUpRequestDTO.getPassword())
                .familyCode(commonUserSignUpRequestDTO.getFamilyCode())
                .role(Role.USER)
                .build();
    }

    public static CommonUserResponse.CommonUserIdResponseDTO toCommonUserIdResponseDTO(CommonUser commonUser) {
        return CommonUserResponse.CommonUserIdResponseDTO.builder()
                .userId(commonUser.getId())
                .build();
    }
}
