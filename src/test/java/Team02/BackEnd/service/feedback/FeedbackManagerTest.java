package Team02.BackEnd.service.feedback;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createFeedback;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.feedbackDto.FeedbackApiDataDto;
import Team02.BackEnd.dto.feedbackDto.FeedbackResponseDto.GetFeedbackToFastApiDto;
import Team02.BackEnd.dto.recordDto.RecordRequestDto.GetRespondDto;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class FeedbackManagerTest {

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private FeedbackApiService feedbackApiService;

    @Mock
    private FeedbackCheckService feedbackCheckService;

    @InjectMocks
    private FeedbackManager feedbackManager;

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
    void saveBeforeAudioLink() {
        // given
        GetRespondDto getRespondDto = GetRespondDto.builder()
                .answerId(answer.getId())
                .beforeAudioLink(feedback.getBeforeAudioLink())
                .build();

        // when
        feedbackManager.saveBeforeAudioLink(accessToken, getRespondDto);

        // then
        verify(feedbackService, times(1)).saveBeforeAudioLink(accessToken, getRespondDto);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void createFeedbackData() {
        // given
        UserVoiceDto userVoiceDto = UserVoiceDto.builder()
                .id(user.getId())
                .name(user.getName())
                .voiceUrl(user.getVoiceUrl())
                .build();
        FeedbackApiDataDto feedbackApiDataDto = new FeedbackApiDataDto(feedback, feedback.getBeforeAudioLink(),
                List.of(feedback.getBeforeAudioLink()), userVoiceDto);

        GetFeedbackToFastApiDto getFeedbackToFastApiDto = GetFeedbackToFastApiDto.builder()
                .beforeScript(feedback.getBeforeScript())
                .afterScript(feedback.getAfterScript())
                .afterAudioLink(feedback.getAfterAudioLink())
                .feedbackText(feedback.getFeedbackText())
                .build();

        // when
        given(feedbackCheckService.getDataForFeedbackApi(accessToken, answer.getId())).willReturn(feedbackApiDataDto);
        given(feedbackApiService.getFeedbackFromFastApi(accessToken, feedbackApiDataDto.beforeAudioLink(),
                feedbackApiDataDto.pastAudioLinks(), feedbackApiDataDto.userData(), answer.getId())).willReturn(
                getFeedbackToFastApiDto);

        feedbackManager.createFeedbackData(accessToken, answer.getId());

        // then
        verify(feedbackService, times(1)).updateFeedbackData(feedbackApiDataDto.feedback(), getFeedbackToFastApiDto);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getFeedbackByAnswerId() {
        // given

        // when
        given(feedbackCheckService.getFeedbackByAnswerId(answer.getId())).willReturn(feedback);

        Feedback result = feedbackManager.getFeedbackByAnswerId(answer.getId());

        // then
        assertThat(result).isEqualTo(feedback);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void doSpeechToday() {
        // given

        // when
        given(feedbackCheckService.doSpeechToday(accessToken)).willReturn(true);

        Boolean result = feedbackManager.doSpeechToday(accessToken);

        // then
        assertThat(result).isTrue();
    }
}