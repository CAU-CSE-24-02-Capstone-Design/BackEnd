package Team02.BackEnd.service.feedback;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createFeedback;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import Team02.BackEnd.dto.recordDto.RecordRequestDto.GetRespondDto;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import Team02.BackEnd.repository.FeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackApiService feedbackApiService;
    @Mock
    private FeedbackCheckService feedbackCheckService;
    @Mock
    private UserCheckService userCheckService;
    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

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

    @DisplayName("스피치에 대한 피드백 데이터 생성하기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void createFeedbackData() {
        // given
        GetFeedbackToFastApiDto response = GetFeedbackToFastApiDto.builder()
                .beforeScript("bs")
                .afterScript("as")
                .afterAudioLink("aa")
                .feedbackText("ft")
                .build();
        List<String> pastAudioLinks = List.of("1", "2", "3");
        UserVoiceDto userData = new UserVoiceDto(user.getId(), user.getName(), user.getVoiceUrl());

        // when
        given(feedbackCheckService.getFeedbackByAnswerId(answer.getId())).willReturn(feedback);
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(feedbackCheckService.getPastAudioLinks(user.getId())).willReturn(pastAudioLinks);
        given(feedbackApiService.getFeedbackFromFastApi(accessToken, feedback.getBeforeAudioLink(), pastAudioLinks,
                userData, answer.getId())).willReturn(
                response);

        feedbackService.createFeedbackData(accessToken, answer.getId());

        // then
//        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @DisplayName("1분 스피치 녹음본 저장하기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveBeforeAudioLink() {
        // given
        GetRespondDto getRespondDto = GetRespondDto.builder()
                .answerId(answer.getId())
                .beforeAudioLink("bs")
                .build();

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.getAnswerByAnswerId(answer.getId())).willReturn(answer);

        feedbackService.saveBeforeAudioLink(accessToken, getRespondDto);

        // then
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }
}