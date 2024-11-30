package Team02.BackEnd.controller;

import static Team02.BackEnd.util.TestUtil.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import Team02.BackEnd.config.TestSecurityConfig;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
import Team02.BackEnd.oauth.controller.OauthController;
import Team02.BackEnd.oauth.service.OauthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OauthController.class)
@Import(TestSecurityConfig.class)
class OauthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OauthService oauthService;

    @DisplayName("AuthCode와 함께 Redirect")
    @Test
    void redirectAuthCodeRequestUrl() throws Exception {
        // given
        OauthServerType oauthServerType = OauthServerType.KAKAO;
        String redirectUrl = "redirectUrl";

        // when
        given(oauthService.getAuthCodeRequestUrl(oauthServerType)).willReturn(redirectUrl);

        // then
        mockMvc.perform(get("/api/spring/oauth/{oauthServerType}", oauthServerType))
                .andExpect(status().is3xxRedirection())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("OAUTH2000"))
                .andExpect(jsonPath("$.message").value("소셜 로그인 리다이렉트 성공"));
    }

    @DisplayName("token을 받아서 실제 로그인하기")
    @Test
    void login() throws Exception {
        // given
        OauthServerType oauthServerType = OauthServerType.GOOGLE;
        String code = "code";
        User user = createUser();

        // when
        given(oauthService.login(any(HttpServletResponse.class), eq(oauthServerType), eq(code))).willReturn(user);

        // then
        mockMvc.perform(get("/api/spring/oauth/login/{oauthServerType}", oauthServerType)
                        .param("code", code))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("OAUTH2001"))
                .andExpect(jsonPath("$.message").value("소셜 로그인 성공"));
    }
}
