package Team02.BackEnd.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.selfFeedbackDto.SelfFeedbackRequestDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.oauth.OauthServerType;
import Team02.BackEnd.service.selffeedback.SelfFeedbackCheckService;
import Team02.BackEnd.service.selffeedback.SelfFeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SelfFeedbackController.class)
class SelfFeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SelfFeedbackService selfFeedbackService;

    @MockBean
    private SelfFeedbackCheckService selfFeedbackCheckService;

    @MockBean
    private JwtService jwtService;

    @DisplayName("셀프 피드백 저장하기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveSelfFeedback() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        Long answerId = 1L;
        SelfFeedbackRequestDto.SaveSelfFeedbackDto saveSelfFeedbackDto = SelfFeedbackRequestDto.SaveSelfFeedbackDto.builder()
                .feedback("feedback")
                .build();

        // when
        // then
        mockMvc.perform(post("/api/spring/self-feedbacks")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .param("answerId", String.valueOf(answerId))
                        .content(objectMapper.writeValueAsString(saveSelfFeedbackDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("SELFFEEDBACK2000"))
                .andExpect(jsonPath("$.message").value("셀프 피드백 저장 성공"));
    }

    @DisplayName("가장 최근의 셀프 피드백 받아오기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getBeforeSelfFeedback() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        User user = createUser();
        Answer answer = createAnswer(user);
        SelfFeedback selfFeedback = createSelfFeedback(answer);

        // when
        given(selfFeedbackCheckService.getLatestSelfFeedback(accessToken)).willReturn(selfFeedback);

        // then
        mockMvc.perform(get("/api/spring/self-feedbacks/latest-feedbacks")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("SELFFEEDBACK2001"))
                .andExpect(jsonPath("$.message").value("셀프 피드백 가져오기 성공"))
                .andExpect(jsonPath("$.result.feedback").value(selfFeedback.getFeedback()));
    }

    private User createUser() {
        return User.builder()
                .email("tlsgusdn4818@gmail.com")
                .name("Hyun")
                .role(Role.USER)
                .oauthId(new OauthId("1", OauthServerType.GOOGLE))
                .voiceUrl("voiceUrl")
                .questionNumber(1L)
                .build();
    }

    private Answer createAnswer(final User user) {
        return Answer.builder()
                .user(user)
                .question(null)
                .evaluation(0)
                .build();
    }

    private SelfFeedback createSelfFeedback(final Answer answer) {
        return SelfFeedback.builder()
                .feedback("feedback")
                .answer(answer)
                .build();
    }
}