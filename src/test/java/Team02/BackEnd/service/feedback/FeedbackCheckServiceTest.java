package Team02.BackEnd.service.feedback;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createFeedback;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.FeedbackHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.FeedbackRepository;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class FeedbackCheckServiceTest {

    @Mock
    private UserCheckService userCheckService;
    @Mock
    private AnswerCheckService answerCheckService;
    @Mock
    private FeedbackRepository feedbackRepository;

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
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        feedback = createFeedback(user, answer);
    }

    @DisplayName("오늘 스피치를 진행했는지 확인한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void doSpeechToday() {
        // given
        List<Answer> answers = List.of(answer);

        // when
        given(userCheckService.getUserByToken(accessToken)).willReturn(user);
        given(answerCheckService.getAnswersByUser(user)).willReturn(answers);
        given(feedbackRepository.findByAnswerId(answer.getId())).willReturn(Optional.of(feedback));

        boolean doSpeechToday = feedbackCheckService.doSpeechToday(accessToken);

        // then
        assertThat(doSpeechToday).isFalse();
    }

    @DisplayName("이전 녹음 파일 링크들을 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getPastAudioLinks() {
        // given
        PageRequest pageRequest = PageRequest.of(0, LIMIT_PAST_AUDIO_NUMBER, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Feedback> feedbackPage = new PageImpl<>(List.of(feedback));

        // when
        given(feedbackRepository.findByUserId(user.getId(), pageRequest)).willReturn(feedbackPage);
        List<String> pastAudioLinks = feedbackCheckService.getPastAudioLinks(user);

        // then
        assertThat(pastAudioLinks).contains("ba");
    }

    @DisplayName("Answer와 연결된 Feedback이 있는지 확인한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void isFeedbackExistsWithAnswer() {
        // given

        // when
        given(feedbackRepository.findByAnswerId(answer.getId())).willReturn(Optional.of(feedback));
        boolean isFeedbackExists = feedbackCheckService.isFeedbackExistsWithAnswer(answer);

        // then
        assertThat(isFeedbackExists).isEqualTo(true);
    }

    @DisplayName("Answer와 연결된 Feedback을 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getFeedbackByAnswerId() {
        // given

        // when
        given(feedbackRepository.findByAnswerId(answer.getId())).willReturn(Optional.of(feedback));
        Feedback findFeedback = feedbackCheckService.getFeedbackByAnswerId(answer.getId());

        // then
        assertThat(findFeedback).isEqualTo(feedback);
    }

    @DisplayName("Answer와 연결된 Feedback이 없으면 _FEEDBACK_NOT_FOUND 에러를 반환한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getNoFeedbackByAnswerId() {
        // given

        // when
        given(feedbackRepository.findByAnswerId(answer.getId())).willReturn(Optional.empty());
        FeedbackHandler exception = assertThrows(FeedbackHandler.class,
                () -> feedbackCheckService.getFeedbackByAnswerId(answer.getId()));

        // then
        assertThat(ErrorStatus._FEEDBACK_NOT_FOUND.getCode()).isEqualTo(exception.getCode().getReason().getCode());
    }
}
