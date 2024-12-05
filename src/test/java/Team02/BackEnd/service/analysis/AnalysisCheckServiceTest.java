package Team02.BackEnd.service.analysis;

import static Team02.BackEnd.util.TestUtil.createAnalysis;
import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnalysisHandler;
import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnalysisRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
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

    @InjectMocks
    private AnalysisCheckService analysisCheckService;

    private final int NUMBER_OF_USER_SPEECH = 7;
    private String accessToken;
    private User user;
    private Question question;
    private Answer answer;
    private Analysis analysis;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        analysis = createAnalysis(user);
    }

    @DisplayName("7일 분석 리포트를 생성할 수 있는 상태인지 확인한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void canSaveAnalysis() {
        // given
        List<Answer> answers = List.of(answer);

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.getAnswerByUserWithSize(user, NUMBER_OF_USER_SPEECH)).willReturn(answers);
        given(feedbackCheckService.isFeedbackExistsWithAnswer(answer)).willReturn(true);

        boolean canSaveAnalysis = analysisCheckService.canSaveAnalysis(accessToken);

        // then
        assertThat(canSaveAnalysis).isFalse();
    }

//    @DisplayName("사용자의 가장 최근 일주일 분석 리포트를 가져온다")
//    @Test
//    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
//    void getAnalysis() {
//        // given
//
//        // when
//        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
//        given(analysisRepository.findMostRecentAnalysisByUserId(user.getId())).willReturn(analysis);
//        String analysisText = analysisCheckService.getAnalysis(accessToken);
//
//        // then
//        assertThat(analysisText).isEqualTo(analysis.getAnalysisText());
//    }

//    @DisplayName("사용자의 가장 최근 일주일 분석 리포트가 없으면 _ANALYSIS_NOT_FOUND 에러를 반환한다")
//    @Test
//    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
//    void getNoAnalysis() {
//        // given
//        Pageable pageable = PageRequest.of(0, 1);
//
//        // when
//        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
//        given(analysisRepository.findMostRecentAnalysisByUserId(user.getId(), pageable)).willReturn(null);
//
//        AnalysisHandler exception = assertThrows(AnalysisHandler.class,
//                () -> analysisCheckService.getAnalysis(accessToken));
//
//        // then
//        assertThat(ErrorStatus._ANALYSIS_NOT_FOUND.getCode()).isEqualTo(exception.getCode().getReason().getCode());
//    }
}
