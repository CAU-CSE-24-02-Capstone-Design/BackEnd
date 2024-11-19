package Team02.BackEnd.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FeedbackService feedbackService;

    @MockBean
    private JwtService jwtService;

    @DisplayName("피드백 생성 api")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void createFeedback() throws Exception {
        // given
        Long answerId = 1L;
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        // when
        // then
        mockMvc.perform(post("/api/spring/feedbacks")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .param("answerId", String.valueOf(answerId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("FEEDBACK2000"))
                .andExpect(jsonPath("$.message").value("피드백 저장 성공"));
    }

    @DisplayName("피드백 데이터 요청 api")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getFeedback() throws Exception {
        // given
        Long answerId = 1L;
        Feedback mockFeedback = Feedback.builder()
                .beforeAudioLink("ba")
                .beforeScript("bs")
                .afterAudioLink("aa")
                .afterScript("as")
                .feedbackText("ft")
                .build();

        // when
        given(feedbackService.getFeedbackByAnswerId(answerId))
                .willReturn(mockFeedback);

        // then
        String expectedJson = """
                {
                  "isSuccess": true,
                  "code": "FEEDBACK2001",
                  "message": "피드백 가져오기 성공",
                  "result": {
                    "beforeScript": "bs",
                    "beforeAudioLink": "ba",
                    "afterScript": "as",
                    "afterAudioLink": "aa",
                    "feedbackText": "ft"
                  }
                }
                """;
        mockMvc.perform(get("/api/spring/feedbacks")
                        .param("answerId", String.valueOf(answerId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andDo(print());
    }
}