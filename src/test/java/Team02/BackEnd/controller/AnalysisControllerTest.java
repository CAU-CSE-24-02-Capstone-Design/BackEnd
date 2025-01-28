package Team02.BackEnd.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAvailabilityAnalysisDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.service.analysis.AnalysisCheckService;
import Team02.BackEnd.service.analysis.AnalysisManager;
import Team02.BackEnd.service.analysis.AnalysisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AnalysisController.class)
class AnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnalysisManager analysisManager;

    @MockBean
    private JwtService jwtService;

    @DisplayName("일주일 분석 리포트 생성 가능 여부를 확인한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void canSaveAnalysis() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        // when

        // then
        mockMvc.perform(get("/api/spring/analysis/available")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("STATISTICS2004"))
                .andExpect(jsonPath("$.message").value("유저 언어 습관 분석 생성하기 가능"))
                .andExpect(jsonPath("$.result.canSaveAnalysis").value(false));
        verify(analysisManager, times(1)).canSaveAnalysis(accessToken);
    }

    @DisplayName("일주일 분석 리포트를 생성한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void createAnalysis() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        // when

        // then
        mockMvc.perform(post("/api/spring/analysis")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("STATISTICS2002"))
                .andExpect(jsonPath("$.message").value("유저 언어 습관 분석 생성하기 성공"));
        verify(analysisManager, times(1)).saveAnalysis(accessToken);
    }

    @DisplayName("일주일 분석 리포트를 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnalysis() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        // when

        // then
        mockMvc.perform(get("/api/spring/analysis")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("STATISTICS2003"))
                .andExpect(jsonPath("$.message").value("유저 언어 습관 분석 가져오기 성공"));
        verify(analysisManager, times(1)).getAnalysis(accessToken);
    }
}