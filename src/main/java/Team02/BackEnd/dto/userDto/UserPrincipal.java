package Team02.BackEnd.dto.userDto;

import Team02.BackEnd.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal {

    private String email;
    private Role role;
}
