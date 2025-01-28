package Team02.BackEnd.service.analysis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import Team02.BackEnd.config.TestConfig;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto;
import Team02.BackEnd.validator.AnalysisValidator;
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

@RestClientTest(AnalysisApiService.class)
@Import(TestConfig.class)
class AnalysisApiServiceTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AnalysisValidator analysisValidator;
    @Autowired
    private AnalysisApiService analysisApiService;
    @Autowired
    private MockRestServiceServer mockServer;

    private static final String FASTAPI_API_URL = "https://peachmentor.com/api/fastapi/records/analyses";
    private String accessToken;

    @BeforeEach
    void setUp() {
        this.mockServer = MockRestServiceServer.bindTo(restTemplate).build();
        accessToken = "accessToken";
    }

    @AfterEach
    void tearDown() {
        mockServer.reset();
    }

    @DisplayName("사용자의 일주일치 스피치에 대한 분석 데이터 받아오기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnalysisFromFastApi() {
        // given
        List<String> questions = List.of("question1", "question2");
        List<String> beforeScripts = List.of("beforeScript1", "beforeScript2");

        // when
        mockServer.expect(requestTo(FASTAPI_API_URL))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", "Bearer " + accessToken))
                .andRespond(withSuccess(
                        "{\"analysisText\": [[\"GOOD\"]]}",
                        MediaType.APPLICATION_JSON));

        AnalysisResponseDto.GetAnalysisFromFastApiDto response = analysisApiService.getAnalysisFromFastApi(
                accessToken,
                questions,
                beforeScripts);

        // then
        mockServer.verify();
        assertThat(response.getAnalysisText()).containsExactly(List.of("GOOD"));
    }
}