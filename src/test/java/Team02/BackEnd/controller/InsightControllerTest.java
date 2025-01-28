package Team02.BackEnd.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import Team02.BackEnd.dto.insightDto.InsightRequestDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.service.insight.InsightManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(InsightController.class)
class InsightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InsightManager insightManager;

    @MockBean
    private JwtService jwtService;

    @DisplayName("AI 인사이트 저장하기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveAiInsight() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        Long answerId = 1L;
        List<String> insights = createInsights();

        InsightRequestDto.GetInsightDto getInsightDto = InsightRequestDto.GetInsightDto.builder()
                .insight(insights)
                .build();

        // when
        // then
        mockMvc.perform(post("/api/spring/insights")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .param("answerId", String.valueOf(answerId))
                        .content(objectMapper.writeValueAsString(getInsightDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("INSIGHT2000"))
                .andExpect(jsonPath("$.message").value("인사이트 저장 성공"));
        verify(insightManager, times(1)).saveAiInsight(getInsightDto.getInsight(), answerId);
    }

    @DisplayName("AI 인사이트 가져오기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAiInsight() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        Long answerId = 1L;

        // when
        List<String> insights = createInsights();
        given(insightManager.getAiInsight(answerId)).willReturn(insights);

        // then
        String expectedJson = """
                {
                  "isSuccess": true,
                  "code": "INSIGHT2001",
                  "message": "인사이트 가져오기 성공",
                  "result":{"insight":["insight1","insight2"]}
                }
                """;
        mockMvc.perform(get("/api/spring/insights")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .param("answerId", String.valueOf(answerId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    private List<String> createInsights() {
        return List.of("insight1", "insight2");
    }
}