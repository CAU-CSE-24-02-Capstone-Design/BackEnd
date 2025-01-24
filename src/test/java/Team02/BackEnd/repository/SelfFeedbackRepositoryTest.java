package Team02.BackEnd.repository;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createSelfFeedback;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class SelfFeedbackRepositoryTest {

    @Autowired
    private SelfFeedbackRepository selfFeedbackRepository;

    private User user;
    private Question question;
    private Answer answer;
    private SelfFeedback selfFeedback;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        selfFeedback = createSelfFeedback(answer);
    }

    @DisplayName("AnswerId로 SelfFeedback 가져오기.")
    @Transactional
    @Test
    void findByAnswerId() {
        // given
        selfFeedbackRepository.save(selfFeedback);

        // when
        SelfFeedback selfFeedbackResult = selfFeedbackRepository.findByAnswerId(answer.getId());

        // then
        assertEquals(selfFeedback, selfFeedbackResult);
    }

    @DisplayName("Answer와 연결된 SelfFeedback 있는지 확인하기.")
    @Transactional
    @Test
    void existsByAnswerId() {
        // given
        selfFeedbackRepository.save(selfFeedback);

        // when
        boolean isExists = selfFeedbackRepository.existsByAnswerId(answer.getId());

        // then
        assertTrue(isExists);
    }

    @DisplayName("AnswerId로 SelfFeedback 내용만 가져오기.")
    @Transactional
    @Test
    void findSelfFeedbackText() {
        // given
        selfFeedbackRepository.save(selfFeedback);

        // when
        String text = selfFeedbackRepository.findSelfFeedbackText(answer.getId()).orElse(null);

        // then
        assertThat(text).isEqualTo(selfFeedback.getFeedback());
    }
}
