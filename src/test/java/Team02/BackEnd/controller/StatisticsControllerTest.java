package Team02.BackEnd.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import Team02.BackEnd.dto.statisticsDto.StatisticsRequestDto;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto.GetStatisticsDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.service.statistics.StatisticsCheckService;
import Team02.BackEnd.service.statistics.StatisticsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StatisticsService statisticsService;

    @MockBean
    private StatisticsCheckService statisticsCheckService;

    @MockBean
    private JwtService jwtService;

    @DisplayName("통계 데이터 저장하기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveStatistics() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        Long answerId = 1L;
        StatisticsRequestDto.GetStatisticsDto getStatisticsDto = StatisticsRequestDto.GetStatisticsDto.builder()
                .gantourCount(1L)
                .silentTime(5.0)
                .answerId(answerId)
                .build();

        // when
        // then
        mockMvc.perform(post("/api/spring/statistics")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(getStatisticsDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("STATISTICS2000"))
                .andExpect(jsonPath("$.message").value("통계 횟수 저장 성공"));
    }

    @DisplayName("통계 데이터 가져오기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getFilterStatistics() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        List<GetStatisticsDto> getStatisticsDtos = createGetStatisticsDtos();

        // when
        given(statisticsCheckService.getFilterStatistics(accessToken)).willReturn(getStatisticsDtos);

        // then
        String expectedJson = """
                {
                  "isSuccess": true,
                  "code": "STATISTICS2001",
                  "message": "유저 통계 데이터 가져오기 성공",
                  "result":[{"day":"2024-11-11","gantourCount":1,"silentTime":5.0},{"day":"2024-11-12","gantourCount":2,"silentTime":7.2}]
                }
                """;

        mockMvc.perform(get("/api/spring/statistics")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("STATISTICS2001"))
                .andExpect(jsonPath("$.message").value("유저 통계 데이터 가져오기 성공"))
                .andExpect(content().json(expectedJson));
    }

    private List<GetStatisticsDto> createGetStatisticsDtos() {
        return List.of(
                GetStatisticsDto.builder()
                        .day(LocalDate.parse("2024-11-11"))
                        .gantourCount(1L)
                        .silentTime(5.0)
                        .build(),
                GetStatisticsDto.builder()
                        .day(LocalDate.parse("2024-11-12"))
                        .gantourCount(2L)
                        .silentTime(7.2)
                        .build()
        );
    }
}