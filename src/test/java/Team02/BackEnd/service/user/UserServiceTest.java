package Team02.BackEnd.service.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.recordDto.RecordRequestDto.GetVoiceUrlDto;
import Team02.BackEnd.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserCheckService userCheckService;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
    }

    @DisplayName("회원 탈퇴")
    @Test
    void signOut() {
    }

    @DisplayName("사용자의 목소리 저장")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void updateRoleAndVoiceUrl() {
        // given
        GetVoiceUrlDto getVoiceUrlDto = GetVoiceUrlDto.builder()
                .voiceUrl("voiceUrl")
                .build();
        User user = mock(User.class);

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        userService.updateRoleAndVoiceUrl(accessToken, getVoiceUrlDto);

        // then
        verify(user, times(1)).updateRole();
        verify(user, times(1)).updateVoiceUrl(getVoiceUrlDto.getVoiceUrl());
        verify(userRepository, times(1)).save(any());
    }
}