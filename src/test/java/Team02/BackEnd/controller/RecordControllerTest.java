package Team02.BackEnd.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import Team02.BackEnd.dto.recordDto.RecordRequestDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.service.FeedbackService;
import Team02.BackEnd.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RecordController.class)
class RecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private FeedbackService feedbackService;

    @MockBean
    private JwtService jwtService;

    @DisplayName("사용자 목소리 녹음 파일 저장")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveVoiceUrl() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        RecordRequestDto.GetVoiceUrlDto getVoiceUrlDto = RecordRequestDto.GetVoiceUrlDto.builder()
                .voiceUrl("voiceUrl")
                .build();

        // when
        // then
        mockMvc.perform(post("/api/spring/records/voices")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(getVoiceUrlDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("RECORD2000"))
                .andExpect(jsonPath("$.message").value("첫 로그인 시 녹음 파일 저장 성공"));
    }

    @DisplayName("피드백 받기 전 스피치 녹음 파일 저장")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void setBeforeAudioLink() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        Long answerId = 1L;
        RecordRequestDto.GetRespondDto getRespondDto = RecordRequestDto.GetRespondDto.builder()
                .answerId(answerId)
                .beforeAudioLink("bs")
                .build();

        // when
        // then
        mockMvc.perform(post("/api/spring/records/speeches")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(getRespondDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("RECORD2001"))
                .andExpect(jsonPath("$.message").value("1분 스피치 녹음 파일 저장 성공"));
    }
}