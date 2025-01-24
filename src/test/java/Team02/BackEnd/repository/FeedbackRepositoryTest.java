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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    private User user;
    private Question question;
    private Answer answer;
    private Answer answer1;
    private Answer answer2;
    private Answer answer3;
    private Feedback feedback;
    private Feedback feedback1;
    private Feedback feedback2;
    private Feedback feedback3;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        answer1 = createAnswer(user, question);
        answer2 = createAnswer(user, question);
        answer3 = createAnswer(user, question);
        feedback = createFeedback(user, answer);
        feedback1 = createFeedback(user, answer1);
        feedback2 = createFeedback(user, answer2);
        feedback3 = createFeedback(user, answer3);
    }

    @DisplayName("AnswerId로 모든 Feedback을 가져온다.")
    @Transactional
    @Test
    void findByAnswerId() {
        // given
        feedbackRepository.save(feedback);

        // when
        Optional<Feedback> feedbackResult = feedbackRepository.findByAnswerId(answer.getId());

        // then
        assertTrue(feedbackResult.isPresent());
        assertEquals(feedback, feedbackResult.get());
    }

    @DisplayName("Answer와 연결된 Feedback이 있는지 확인한다.")
    @Transactional
    @Test
    void existsByAnswerId() {
        // given
        feedbackRepository.save(feedback);

        // when
        boolean isExists = feedbackRepository.existsByAnswerId(answer.getId());

        // then
        assertTrue(isExists);
    }

    @DisplayName("AnswerId로 Feedback의 CreatedAt 가져오기.")
    @Transactional
    @Test
    void findCreatedAtByAnswerId() {
        // given
        feedbackRepository.save(feedback);

        // when
        LocalDateTime createdAt = feedbackRepository.findCreatedAtByAnswerId(answer.getId());

        // then
        assertThat(createdAt).isEqualTo(feedback.getCreatedAt());
    }

    @DisplayName("UserId로 최근 BeforeAudioLink 특정 개수만큼 가져오기.")
    @Transactional
    @Test
    void findLatestBeforeAudioLinksByUserIdWithSize() {
        // given
        feedbackRepository.save(feedback1);
        feedbackRepository.save(feedback2);
        feedbackRepository.save(feedback3);

        // when
        Pageable pageable = PageRequest.of(0, 3);
        List<String> beforeAudioLinks = feedbackRepository.findLatestBeforeAudioLinksByUserIdWithSize(user.getId(),
                pageable);

        // then
        assertThat(beforeAudioLinks).hasSize(3);
    }

    @DisplayName("UserId로 모든 BeforeAudioLink 가져오기")
    @Transactional
    @Test
    void findAllBeforeAudioLinksByUserId() {
        // given
        feedbackRepository.save(feedback1);
        feedbackRepository.save(feedback2);
        feedbackRepository.save(feedback3);

        // when
        List<String> beforeAudioLinks = feedbackRepository.findAllBeforeAudioLinksByUserId(user.getId());

        // then
        assertThat(beforeAudioLinks).hasSize(3);
    }

    @DisplayName("UserId로 최근 BeforeScripts를 특정 개수만큼 가져온다.")
    @Transactional
    @Test
    void findLatestBeforeScriptsByUserIdWithSize() {
        // given
        feedbackRepository.save(feedback1);
        feedbackRepository.save(feedback2);
        feedbackRepository.save(feedback3);

        // when
        Pageable pageable = PageRequest.of(0, 2);
        List<String> beforeScripts = feedbackRepository.findLatestBeforeScriptsByUserIdWithSize(user.getId(), pageable);

        // then
        assertThat(beforeScripts).hasSize(2);
    }
}
