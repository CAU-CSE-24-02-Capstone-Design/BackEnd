package Team02.BackEnd.service.analysis;

import static Team02.BackEnd.util.TestUtil.createAnalysis;
import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createFeedback;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.analysisDto.AnalysisApiDataDto;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisDto;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisFromFastApiDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class AnalysisManagerTest {

    @Mock
    private AnalysisService analysisService;
    @Mock
    private AnalysisCheckService analysisCheckService;
    @Mock
    private AnalysisApiService analysisApiService;

    @InjectMocks
    private AnalysisManager analysisManager;

    private String accessToken;
    private User user;
    private Question question;
    private Answer answer;
    private Feedback feedback;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        feedback = createFeedback(user, answer);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveAnalysis() {
        // given
        List<String> questions = List.of(question.getDescription());
        List<String> beforeScripts = List.of(feedback.getBeforeScript());
        AnalysisApiDataDto analysisApiDataDto = new AnalysisApiDataDto(user, questions, beforeScripts);
        List<List<String>> dummyAnalysisText = List.of(
                List.of("Dummy analysis line 1 for user", "Dummy analysis line 2 for user"),
                List.of("Dummy analysis line 3 for user", "Dummy analysis line 4 for user")
        );
        GetAnalysisFromFastApiDto getAnalysisFromFastApiDto = new GetAnalysisFromFastApiDto(dummyAnalysisText);

        // when
        given(analysisCheckService.getDataForAnalysisApi(accessToken)).willReturn(analysisApiDataDto);
        given(analysisApiService.getAnalysisFromFastApi(accessToken, analysisApiDataDto.questions(),
                analysisApiDataDto.beforeScripts())).willReturn(getAnalysisFromFastApiDto);
        analysisManager.saveAnalysis(accessToken);

        // then
        verify(analysisService, times(1)).saveAnalysis(getAnalysisFromFastApiDto.getAnalysisText(), user);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void canSaveAnalysis() {
        // given

        // when
        given(analysisCheckService.canSaveAnalysis(accessToken)).willReturn(true);

        boolean canSave = analysisManager.canSaveAnalysis(accessToken);

        // then
        assertThat(canSave).isTrue();
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnalysis() {
        // given
        List<List<String>> dummyAnalysisText = List.of(
                List.of("Dummy analysis line 1 for user", "Dummy analysis line 2 for user"),
                List.of("Dummy analysis line 3 for user", "Dummy analysis line 4 for user")
        );
        String firstDate = "firstDate";
        String lastDate = "lastDate";
        GetAnalysisDto getAnalysisDto = GetAnalysisDto.builder()
                .analysisText(dummyAnalysisText)
                .firstDate(firstDate)
                .lastDate(lastDate)
                .build();

        // when
        given(analysisCheckService.getAnalysis(accessToken)).willReturn(getAnalysisDto);

        GetAnalysisDto result = analysisManager.getAnalysis(accessToken);

        // then
        assertThat(result).isEqualTo(getAnalysisDto);
    }
}