package Team02.BackEnd.service.feedback;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createFeedback;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.feedbackDto.FeedbackApiDataDto;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import Team02.BackEnd.repository.FeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import Team02.BackEnd.validator.FeedbackValidator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class FeedbackCheckServiceTest {

    @Mock
    private UserCheckService userCheckService;
    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private FeedbackValidator feedbackValidator;

    @InjectMocks
    private FeedbackCheckService feedbackCheckService;

    private static final int LIMIT_PAST_AUDIO_NUMBER = 5;
    private String accessToken;
    private User user;
    private Question question;
    private Answer answer;
    private Feedback feedback;

    @BeforeEach
    void setUp() {
        accessToken = "accessToken";
        user = createUser(1L);
        question = createQuestion(1L, "description");
        answer = createAnswer(1L, user, question);
        feedback = createFeedback(1L, user, answer);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void doSpeechToday() {
        // given
        List<Long> answerIds = List.of(answer.getId());

        // when
        given(userCheckService.getUserIdByToken(accessToken)).willReturn(user.getId());
        given(answerCheckService.getAnswerIdsByUserId(user.getId())).willReturn(answerIds);
        given(feedbackRepository.existsByAnswerId(answer.getId())).willReturn(true);
        given(feedbackRepository.findCreatedAtByAnswerId(answer.getId())).willReturn(feedback.getCreatedAt());

        boolean doSpeechToday = feedbackCheckService.doSpeechToday(accessToken);

        // then
        assertThat(doSpeechToday).isFalse();
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getPastAudioLinks() {
        // given
        Pageable pageable = PageRequest.of(0, LIMIT_PAST_AUDIO_NUMBER);
        List<String> beforeAudioLinks = List.of(feedback.getBeforeAudioLink());

        // when
        given(feedbackRepository.findLatestBeforeAudioLinksByUserIdWithSize(user.getId(), pageable)).willReturn(
                beforeAudioLinks);
        List<String> pastAudioLinks = feedbackCheckService.getPastAudioLinks(user.getId());

        // then
        assertThat(pastAudioLinks).contains("ba");
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getDataForFeedbackApi() {
        // given
        UserVoiceDto userVoiceDto = UserVoiceDto.builder()
                .id(user.getId())
                .name(user.getName())
                .voiceUrl(user.getVoiceUrl())
                .build();
        Pageable pageable = PageRequest.of(0, LIMIT_PAST_AUDIO_NUMBER);
        List<String> beforeAudioLinks = List.of(feedback.getBeforeAudioLink());

        // when
        given(feedbackRepository.findByAnswerId(answer.getId())).willReturn(Optional.of(feedback));
        given(userCheckService.getUserDataByToken(accessToken)).willReturn(userVoiceDto);
        given(feedbackRepository.findLatestBeforeAudioLinksByUserIdWithSize(user.getId(), pageable)).willReturn(
                beforeAudioLinks);

        FeedbackApiDataDto feedbackApiDataDto = feedbackCheckService.getDataForFeedbackApi(accessToken, user.getId());

        // then
        assertThat(feedbackApiDataDto).isNotNull();
        assertThat(feedbackApiDataDto.beforeAudioLink()).isEqualTo(beforeAudioLinks.get(0));
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void findBeforeScriptByUser() {
        // given
        Pageable pageable = PageRequest.of(0, 7);
        List<String> expectedBeforeScripts = List.of(feedback.getBeforeScript());

        // when
        given(feedbackRepository.findLatestBeforeScriptsByUserIdWithSize(user.getId(), pageable)).willReturn(
                expectedBeforeScripts);
        List<String> beforeScripts = feedbackCheckService.findBeforeScriptByUser(user, 7);

        // then
        assertThat(beforeScripts).isEqualTo(expectedBeforeScripts);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void isFeedbackExistsWithAnswerId() {
        // given

        // when
        given(feedbackRepository.existsByAnswerId(answer.getId())).willReturn(true);

        boolean isExists = feedbackCheckService.isFeedbackExistsWithAnswerId(answer.getId());

        // then
        assertThat(isExists).isTrue();
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getFeedbackByAnswerId() {
        // given

        // when
        given(feedbackRepository.findByAnswerId(answer.getId())).willReturn(Optional.of(feedback));

        Feedback result = feedbackCheckService.getFeedbackByAnswerId(answer.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getAnswer()).isEqualTo(answer);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getFeedbackCreatedAtByAnswerId() {
        // given

        // when
        given(feedbackRepository.findCreatedAtByAnswerId(answer.getId())).willReturn(feedback.getCreatedAt());

        LocalDateTime result = feedbackCheckService.getFeedbackCreatedAtByAnswerId(answer.getId());

        // then
        assertThat(result).isEqualTo(feedback.getCreatedAt());
    }
}
