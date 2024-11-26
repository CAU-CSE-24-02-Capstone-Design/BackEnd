package Team02.BackEnd.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @DisplayName("회원 탈퇴")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void signOut() throws Exception {
        // given
        String accessToken = "mockAccessToken";
        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);

        // when
        // then
        mockMvc.perform(delete("/api/spring/sign-out")
                        .with(csrf())
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("USER2000"))
                .andExpect(jsonPath("$.message").value("회원 탈퇴 성공"));
    }

//    @DisplayName("사용자의 특정 년, 월에 대한 스피치 기록 가져오기")
//    @Test
//    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
//    void getDatesWhenUserDid() throws Exception {
//        // given
//        String accessToken = "mockAccessToken";
//        given(jwtService.createAccessToken("tlsgusdn4818@gmail.com")).willReturn(accessToken);
//
//        String year = "2024";
//        String month = "11";
//
//        // when
//        Long[] answerIdDidThisPeriod = Stream.generate(() -> 0L).
//                limit(32).
//                toArray(Long[]::new);
//        given(userService.getDatesWhenUserDid(accessToken, year, month)).willReturn(answerIdDidThisPeriod);
//
//        // then
//        mockMvc.perform(get("/api/spring/calendars")
//                        .with(csrf())
//                        .header("Authorization", "Bearer " + accessToken)
//                        .param("year", year)
//                        .param("month", month)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.isSuccess").value(true))
//                .andExpect(jsonPath("$.code").value("CALENDAR2000"))
//                .andExpect(jsonPath("$.message").value("유저가 참여한 날짜 가져오기 성공"))
//                .andExpect(jsonPath("$.result", hasSize(32)))
//                .andExpect(jsonPath("$.result[0]", equalTo(0)))
//                .andExpect(jsonPath("$.result[31]", equalTo(0)));
//    }
}