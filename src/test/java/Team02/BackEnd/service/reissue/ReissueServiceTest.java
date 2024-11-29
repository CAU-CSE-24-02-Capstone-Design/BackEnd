package Team02.BackEnd.service.reissue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.RefreshTokenHandler;
import Team02.BackEnd.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
class ReissueServiceTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ReissueService reissueService;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    private String refreshToken;
    private String email;

    @BeforeEach
    void setUp() {
        refreshToken = "refreshToken";
        email = "tlsgusdn4818@gmail.com";
    }

    @DisplayName("refreshToken 쿠키에서 꺼내기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getRefreshTokenInCookie() {
        // given

        // when
        given(jwtService.extractRefreshToken(request)).willReturn(Optional.of(refreshToken));
        String findRefreshToken = reissueService.getRefreshTokenInCookie(request);

        // then
        assertThat(findRefreshToken).isEqualTo(refreshToken);
    }

    @DisplayName("refreshToken이 쿠키에 없으면 _REFRESHTOKEN_NOT_FOUND 에러를 반환한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getNoRefreshTokenInCookie() {
        // given

        // when
        given(jwtService.extractRefreshToken(request)).willReturn(Optional.empty());
        RefreshTokenHandler exception = assertThrows(RefreshTokenHandler.class, () -> {
            reissueService.getRefreshTokenInCookie(request);
        });

        // then
        assertThat(ErrorStatus._REFRESHTOKEN_NOT_FOUND.getCode()).isEqualTo(exception.getCode().getReason().getCode());
    }

    @DisplayName("refreshToken 재발급")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void reissueToken() {
        // given
        String newAccessToken = "newAccessToken";
        String newRefreshToken = "newRefreshToken";

        // when
        given(jwtService.getEmailFromRefreshToken(refreshToken)).willReturn(email);
        given(jwtService.createAccessToken(email)).willReturn(newAccessToken);
        given(jwtService.createRefreshToken()).willReturn(newRefreshToken);

        reissueService.reissueToken(response, refreshToken);

        // then
        verify(jwtService, times(1)).sendAccessAndRefreshToken(response, newAccessToken, newRefreshToken);
        verify(jwtService, times(1)).deleteRefreshToken(refreshToken);
        verify(jwtService, times(1)).updateRefreshToken(email, newRefreshToken);
    }
}