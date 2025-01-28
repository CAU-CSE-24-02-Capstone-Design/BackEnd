package Team02.BackEnd.converter;

import Team02.BackEnd.dto.userDto.UserResponseDto;
import Team02.BackEnd.dto.userDto.UserResponseDto.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {
    public static UserResponseDto.UserDto toUserDto(final String accessToken) {
        return UserDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
