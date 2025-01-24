package Team02.BackEnd.service.feedback;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createFeedback;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import Team02.BackEnd.config.TestConfig;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import Team02.BackEnd.validator.FeedbackValidator;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@RestClientTest(FeedbackApiService.class)
@Import(TestConfig.class)
public class FeedbackApiServiceTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FeedbackApiService feedbackApiService;
    @Autowired
    private MockRestServiceServer mockServer;

    private static final String FASTAPI_API_URL = "https://peachmentor.com/api/fastapi/records/feedbacks";
    private String accessToken;
    private User user;
    private Question question;
    private Answer answer;
    private Feedback feedback;

    @BeforeEach
    void setUp() {
        this.mockServer = MockRestServiceServer.bindTo(restTemplate).build();

        accessToken = "accessToken";
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        feedback = createFeedback(user, answer);
    }

    @AfterEach
    void tearDown() {
        mockServer.reset();
    }

    @DisplayName("스피치에 대한 피드백 데이터 생성하기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getFeedbackFromFastApi() {
        // given
        List<String> pastAudioLinks = List.of("1", "2");
        UserVoiceDto userData = new UserVoiceDto(user.getId(), user.getName(), user.getVoiceUrl());

        // when
        mockServer.expect(requestTo(FASTAPI_API_URL))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", "Bearer accessToken"))
                .andRespond(withSuccess(
                        "{\"beforeScript\": \"Before script content\", \"afterScript\": \"After script content\", \"afterAudioLink\": \"http://audio.link\", \"feedbackText\": \"Feedback message\"}",
                        MediaType.APPLICATION_JSON));

        GetFeedbackToFastApiDto response = feedbackApiService.getFeedbackFromFastApi(accessToken,
                feedback.getBeforeAudioLink(), pastAudioLinks, userData, answer.getId());

        // then
        mockServer.verify();
        assertThat(response.getBeforeScript()).isEqualTo("Before script content");
        assertThat(response.getAfterScript()).isEqualTo("After script content");
        assertThat(response.getAfterAudioLink()).isEqualTo("http://audio.link");
        assertThat(response.getFeedbackText()).isEqualTo("Feedback message");

    }
}
