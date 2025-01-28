package Team02.BackEnd.service.user;

import Team02.BackEnd.dto.recordDto.RecordRequestDto.GetVoiceUrlDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final UserService userService;

    public String signUp(final HttpServletResponse response) {
        return userService.signUp(response);
    }

    public void signOut(final String accessToken) {
        userService.signOut(accessToken);
    }

    public void updateRoleAndVoiceUrl(final String accessToken, final GetVoiceUrlDto getVoiceUrlDto) {
        userService.updateRoleAndVoiceUrl(accessToken, getVoiceUrlDto);
    }
}
