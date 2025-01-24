package Team02.BackEnd.service.user;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.dto.recordDto.RecordRequestDto.GetVoiceUrlDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {

    @Mock
    private UserCheckService userCheckService;
    @Mock
    private UserService userService;

    @InjectMocks
    private UserManager userManager;

    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void signOut() {
        // given

        // when
        userManager.signOut(accessToken);

        // then
        verify(userService, times(1)).signOut(accessToken);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void updateRoleAndVoiceUrl() {
        // given
        GetVoiceUrlDto getVoiceUrlDto = new GetVoiceUrlDto();

        // when
        userManager.updateRoleAndVoiceUrl(accessToken, getVoiceUrlDto);

        // then
        verify(userService, times(1)).updateRoleAndVoiceUrl(accessToken, getVoiceUrlDto);
    }
}