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
import Team02.BackEnd.dto.answerDto.AnswerDto.AnswerIdDto;
import Team02.BackEnd.dto.userDto.UserDto.UserAnswerIndexDto;
import Team02.BackEnd.repository.AnalysisRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import Team02.BackEnd.validator.AnalysisValidator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class AnalysisCheckServiceTest {

    @Mock
    private UserCheckService userCheckService;
    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private FeedbackCheckService feedbackCheckService;
    @Mock
    private AnalysisRepository analysisRepository;
    @Mock
    private AnalysisValidator analysisValidator;

    @InjectMocks
    private AnalysisCheckService analysisCheckService;

    private final int NUMBER_OF_USER_SPEECH = 7;
    private String accessToken;
    private User user;
    private Question question;
    private Answer answer;
    private Feedback feedback;
    private Analysis analysis;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
        user = createUser(1L);
        question = createQuestion(1L, "description");
        answer = createAnswer(1L, user, question);
        feedback = createFeedback(user, answer);
        analysis = createAnalysis(user);
    }

    @DisplayName("7일 분석 리포트를 생성할 수 있는 상태인지 확인한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void canSaveAnalysis() {
        // given
        List<Long> answerIds = List.of(answer.getId());
        UserAnswerIndexDto userAnswerIndexDto = UserAnswerIndexDto.builder()
                .id(user.getId())
                .analyzeCompleteAnswerIndex(user.getAnalyzeCompleteAnswerIndex())
                .build();

        // when
        given(userCheckService.getUserAnswerIndexByToken(accessToken)).willReturn(userAnswerIndexDto);
        given(answerCheckService.getAnswerIdsByUserIdWithSize(user.getId(), NUMBER_OF_USER_SPEECH)).willReturn(
                answerIds);
        given(feedbackCheckService.isFeedbackExistsWithAnswerId(answer.getId())).willReturn(true);

        boolean canSaveAnalysis = analysisCheckService.canSaveAnalysis(accessToken);

        // then
        assertThat(canSaveAnalysis).isFalse();
    }

    @DisplayName("사용자의 가장 최근 일주일 분석 리포트를 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnalysis() {
        // given
        AnswerIdDto answerIdDto = AnswerIdDto.builder()
                .id(answer.getId())
                .createdAt(answer.getCreatedAt())
                .build();
        List<AnswerIdDto> answerIdDtos = List.of(answerIdDto);
        List<Analysis> analysisList = List.of(analysis);
        Pageable pageable = PageRequest.of(0, 1);

        // when
        given(userCheckService.getUserIdByToken(accessToken)).willReturn(user.getId());
        given(answerCheckService.getLatestAnswerIdDtosByUserIdWithSize(user.getId(),
                NUMBER_OF_USER_SPEECH)).willReturn(answerIdDtos);
        given(feedbackCheckService.isFeedbackExistsWithAnswerId(answer.getId())).willReturn(true);
        given(analysisRepository.findLatestAnalysisByUserIdWithSize(user.getId(), pageable)).willReturn(analysisList);

        GetAnalysisDto getAnalysisDto = analysisCheckService.getAnalysis(accessToken);

        // then
        assertThat(getAnalysisDto.getAnalysisText()).isEqualTo(analysis.getAnalysisTextAsList());
    }

    @DisplayName("Analysis API 호출을 위한 데이터를 가져온다.")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getDataForAnalysisApi() {
        // given
        List<String> questions = List.of(question.getDescription());
        List<String> beforeScripts = List.of(feedback.getBeforeScript());

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.findQuestionDescriptionsByUser(user, NUMBER_OF_USER_SPEECH)).willReturn(questions);
        given(feedbackCheckService.findBeforeScriptByUser(user, NUMBER_OF_USER_SPEECH)).willReturn(beforeScripts);

        AnalysisApiDataDto analysisApiDataDto = analysisCheckService.getDataForAnalysisApi(accessToken);

        // then
        assertThat(analysisApiDataDto.user().getId()).isEqualTo(user.getId());
        assertThat(analysisApiDataDto.questions()).isEqualTo(questions);
        assertThat(analysisApiDataDto.beforeScripts()).isEqualTo(beforeScripts);
    }
}
