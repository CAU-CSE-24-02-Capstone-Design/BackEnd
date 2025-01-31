package Team02.BackEnd.controller;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createSelfFeedback;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.selfFeedbackDto.SelfFeedbackRequestDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.service.selffeedback.SelfFeedbackManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    private SelfFeedbackManager selfFeedbackManager;

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
        Question question = createQuestion();
        Answer answer = createAnswer(user, question);
        SelfFeedback selfFeedback = createSelfFeedback(answer);

        // when
        given(selfFeedbackManager.getLatestSelfFeedbackText(accessToken)).willReturn(selfFeedback.getFeedback());

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
}
