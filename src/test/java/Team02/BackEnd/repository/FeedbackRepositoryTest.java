package Team02.BackEnd.repository;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createFeedback;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import jakarta.transaction.Transactional;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Question question = createQuestion();
        Answer answer = createAnswer(user, question);
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
        Question question = createQuestion();
        Answer answer1 = createAnswer(user, question);
        Answer answer2 = createAnswer(user, question);
        Answer answer3 = createAnswer(user, question);
        Feedback feedback1 = createFeedback(user, answer1);
        Feedback feedback2 = createFeedback(user, answer2);
        Feedback feedback3 = createFeedback(user, answer3);

        feedbackRepository.save(feedback1);
        feedbackRepository.save(feedback2);
        feedbackRepository.save(feedback3);

        Pageable pageable = PageRequest.of(0, 2);

        // when
        List<Feedback> feedbackPage = feedbackRepository.findByUserId(user.getId(), pageable);

        // then
        assertEquals(2, feedbackPage.size(), "페이지에는 2개의 피드백이 포함되어야 합니다.");
    }

    @DisplayName("사용자의 모든 Feedback 데이터를 가져온다.")
    @Test
    void findAllByUserId() {
        // given
        User user = createUser();
        Question question = createQuestion();
        Answer answer1 = createAnswer(user, question);
        Answer answer2 = createAnswer(user, question);

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

    @DisplayName("사용자의 이전 beforeScript를 가져온다")
    @Transactional
    @Test
    void findBeforeScriptByUser() {
        // given
        User user = createUser();
        Question question = createQuestion();
        Answer answer = createAnswer(user, question);
        Feedback feedback = createFeedback(user, answer);
        feedbackRepository.save(feedback);

        Pageable pageable = PageRequest.of(0, 7, Sort.by("createdAt").descending());

        // when
        List<String> beforeScripts = feedbackRepository.findBeforeScriptByUserId(user.getId(), pageable);

        // then
        List<String> expectedBeforeScripts = List.of(feedback.getBeforeScript());
        assertThat(beforeScripts).isEqualTo(expectedBeforeScripts);
    }
}
