package Team02.BackEnd.service.user;

import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @InjectMocks
    private UserCheckService userCheckService;

    private String accessToken;
    private String email;
    private User user;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
        email = "tlsgusdn4818@gmail.com";
        user = createUser();
    }

    @DisplayName("accessToken으로 사용자 찾기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getUserByToken() {
        // given

        // then
        given(jwtService.extractEmail(accessToken)).willReturn(Optional.of(email));
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        User findUser = userCheckService.getUserByToken(accessToken);

        // when
        assertThat(findUser).isEqualTo(user);
    }

    @DisplayName("accessToken에 맞는 사용자가 없다면 _USER_NOT_FOUND 에러를 반환한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getNoUserByToken() {
        // given

        // then
        given(jwtService.extractEmail(accessToken)).willReturn(Optional.of(email));
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        UserHandler exception = assertThrows(UserHandler.class, () -> {
            userCheckService.getUserByToken(accessToken);
        });

        // when
        assertThat(ErrorStatus._USER_NOT_FOUND.getCode()).isEqualTo(exception.getCode().getReason().getCode());
    }
}