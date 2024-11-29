package Team02.BackEnd.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @DisplayName("답변 기록에 대한 Feedback 데이터를 가져온다.")
    @Test
    void findByAnswerId() {
        // given
        User user = createUser();
        Answer answer = createAnswer(user);
        Feedback feedback = createFeedback(user, answer);

        feedbackRepository.save(feedback);

        // when
        Optional<Feedback> feedbackResult = feedbackRepository.findByAnswerId(answer.getId());

        // then
        assertTrue(feedbackResult.isPresent());
        assertEquals(feedback, feedbackResult.get());
    }

    @DisplayName("사용자의 Feedback 데이터 일부를 페이징해서 가져온다.")
    @Test
    void findByUserId() {
        // given
        User user = createUser();
        Answer answer1 = createAnswer(user);
        Answer answer2 = createAnswer(user);
        Answer answer3 = createAnswer(user);
        Feedback feedback1 = createFeedback(user, answer1);
        Feedback feedback2 = createFeedback(user, answer2);
        Feedback feedback3 = createFeedback(user, answer3);

        feedbackRepository.save(feedback1);
        feedbackRepository.save(feedback2);
        feedbackRepository.save(feedback3);

        PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        Page<Feedback> feedbackPage = feedbackRepository.findByUserId(user.getId(), pageRequest);

        // then
        assertEquals(2, feedbackPage.getContent().size(), "페이지에는 2개의 피드백이 포함되어야 합니다.");
        assertTrue(feedbackPage.getTotalPages() > 1, "전체 피드백이 여러 페이지에 걸쳐야 합니다.");
        assertEquals(3, feedbackPage.getTotalElements(), "전체 피드백은 3개여야 합니다.");
        assertEquals(feedback1, feedbackPage.getContent().get(0), "첫 번째 페이지에 첫 번째 피드백이 있어야 합니다.");
    }

    @DisplayName("사용자의 모든 Feedback 데이터를 가져온다.")
    @Test
    void findAllByUserId() {
        // given
        User user = createUser();

        Answer answer1 = createAnswer(user);
        Answer answer2 = createAnswer(user);

        Feedback feedback1 = createFeedback(user, answer1);
        Feedback feedback2 = createFeedback(user, answer2);

        feedbackRepository.save(feedback1);
        feedbackRepository.save(feedback2);

        // when
        List<Feedback> feedbacks = feedbackRepository.findAllByUserId(user.getId());

        // then
        assertEquals(2, feedbacks.size());
        assertThat(feedbacks).allMatch(feedback -> feedback.getAnswer().getUser().equals(user));
    }

    private Feedback createFeedback(final User user, final Answer answer) {
        return Feedback.builder()
                .beforeAudioLink("ba")
                .beforeScript("bs")
                .afterAudioLink("aa")
                .afterScript("as")
                .feedbackText("ft")
                .user(user)
                .answer(answer)
                .build();
    }

    private User createUser() {
        return User.builder()
                .email("tlsgusdn4818@gmail.com")
                .name("Hyun")
                .role(Role.USER)
                .oauthId(new OauthId("1", OauthServerType.GOOGLE))
                .voiceUrl("voiceUrl")
                .questionNumber(1L)
                .build();
    }

    private Answer createAnswer(final User user) {
        return Answer.builder()
                .user(user)
                .question(null)
                .evaluation(0)
                .build();
    }
}