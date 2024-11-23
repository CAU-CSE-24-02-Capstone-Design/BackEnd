package Team02.BackEnd.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import Team02.BackEnd.domain.Question;
import Team02.BackEnd.dto.questionDto.QuestionResponseDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.service.AnswerService;
import Team02.BackEnd.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuestionService questionService;
    @MockBean
    private AnswerService answerService;

    @MockBean
    private JwtService jwtService;

    @DisplayName("질문을 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getQuestion() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        Question question = createQuestion();
        Long answerId = 1L;

        // when
        given(questionService.getUserQuestion(accessToken)).willReturn(question);
        given(answerService.getAnswerId(accessToken, question)).willReturn(answerId);

        // then
        mockMvc.perform(get("/api/spring/questions")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("QUESTION2000"))
                .andExpect(jsonPath("$.message").value("질문 가져오기 성공"))
                .andExpect(jsonPath("$.result.questionDescription").value(question.getDescription()))
                .andExpect(jsonPath("$.result.answerId").value(answerId));
    }

    private Question createQuestion() {
        return Question.builder()
                .description("description")
                .questionIndex(1L)
                .build();
    }
}