package Team02.BackEnd.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.dto.answerDto.AnswerRequestDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.answer.AnswerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AnswerController.class)
public class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private AnswerCheckService answerCheckService;

    @MockBean
    private JwtService jwtService;

    @DisplayName("스피치에 대한 평가 점수 저장하기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveAnswerEvaluation() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        Long answerId = 1L;
        AnswerRequestDto.AnswerEvaluationRequestDto answerEvaluationRequestDto = AnswerRequestDto.AnswerEvaluationRequestDto.builder()
                .evaluation(1)
                .build();
        // when
        // then
        mockMvc.perform(post("/api/spring/answers/evaluations")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .param("answerId", String.valueOf(answerId))
                        .content(objectMapper.writeValueAsString(answerEvaluationRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("ANSWER2001"))
                .andExpect(jsonPath("$.message").value("스스로 평가 저장 성공"));
    }

    @DisplayName("스피치에 대한 평가 점수 가져오기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnswerEvaluation() throws Exception {
        // given
        Long answerId = 1L;
        int evaluation = 3;
        Answer answer = Answer.builder()
                .id(answerId)
                .evaluation(evaluation)
                .build();
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        // when
        given(answerCheckService.getAnswerByAnswerId(answerId)).willReturn(answer);

        // then
        mockMvc.perform(get("/api/spring/answers/evaluations")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .param("answerId", String.valueOf(answerId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("ANSWER2002"))
                .andExpect(jsonPath("$.message").value("스스로 평가 가져오기 성공"))
                .andExpect(jsonPath("$.result.evaluation").value(evaluation));
    }
}
