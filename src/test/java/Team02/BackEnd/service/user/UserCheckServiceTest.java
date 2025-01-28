package Team02.BackEnd.service.user;

import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.userDto.UserDto.UserAnswerIndexDto;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import Team02.BackEnd.dto.userDto.UserPrincipal;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.UserRepository;
import Team02.BackEnd.validator.UserValidator;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class UserCheckServiceTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserCheckService userCheckService;

    private String accessToken;
    private String email;
    private User user;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
        email = "tlsgusdn4818@gmail.com";
        user = createUser(1L);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserPrincipalByEmail() {
        // given

        // when
        given(userRepository.findRoleByEmail(email)).willReturn(Optional.of(user.getRole()));

        Optional<UserPrincipal> result = userCheckService.getUserPrincipalByEmail(user.getEmail());

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getEmail()).isEqualTo(user.getEmail());
        assertThat(result.get().getRole()).isEqualTo(user.getRole());
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserByToken() {
        // given

        // when
        given(jwtService.extractEmail(accessToken)).willReturn(Optional.of(email));
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        User findUser = userCheckService.getUserByToken(accessToken);

        // then
        assertThat(findUser).isEqualTo(user);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserAnswerIndexByToken() {
        // given
        UserAnswerIndexDto userAnswerIndexDto = UserAnswerIndexDto.builder()
                .id(user.getId())
                .analyzeCompleteAnswerIndex(user.getAnalyzeCompleteAnswerIndex())
                .build();

        // when
        given(jwtService.extractEmail(accessToken)).willReturn(Optional.of(email));
        given(userRepository.findUserAnswerIndexDtoByEmail(user.getEmail())).willReturn(
                Optional.of(userAnswerIndexDto));

        UserAnswerIndexDto result = userCheckService.getUserAnswerIndexByToken(accessToken);

        // then
        assertThat(result).isEqualTo(userAnswerIndexDto);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserIdByToken() {
        // given

        // when
        given(jwtService.extractEmail(accessToken)).willReturn(Optional.of(email));
        given(userRepository.findUserIdByEmail(email)).willReturn(Optional.of(user.getId()));

        Long result = userCheckService.getUserIdByToken(accessToken);

        // then
        assertThat(result).isEqualTo(user.getId());
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserDataByToken() {
        // given
        UserVoiceDto userVoiceDto = UserVoiceDto.builder()
                .id(user.getId())
                .name(user.getName())
                .voiceUrl(user.getVoiceUrl())
                .build();

        // when
        given(jwtService.extractEmail(accessToken)).willReturn(Optional.of(email));
        given(userRepository.findUserVoiceDtoByEmail(user.getEmail())).willReturn(Optional.of(userVoiceDto));

        UserVoiceDto result = userCheckService.getUserDataByToken(accessToken);

        // then
        assertThat(result).isEqualTo(userVoiceDto);
    }
}