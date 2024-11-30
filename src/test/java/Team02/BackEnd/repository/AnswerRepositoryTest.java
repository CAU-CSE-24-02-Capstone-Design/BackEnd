package Team02.BackEnd.repository;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    private User user;
    private Question question;
    private Answer answer1;
    private Answer answer2;
    private Answer answer3;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = createQuestion();
        answer1 = createAnswer(user, question);
        answer2 = createAnswer(user, question);
        answer3 = createAnswer(user, question);
    }

    @DisplayName("사용자의 모든 답변 기록을 가져온다.")
    @Transactional
    @Test
    void findByUserId() {
        // given
        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);

        // when
        List<Answer> answers = answerRepository.findByUserId(user.getId());

        // then
        assertThat(answers).hasSize(3);
        assertThat(answers)
                .allMatch(answer -> answer.getUser().equals(user));
    }

    @DisplayName("사용자의 답변 기록 중 해당 년, 월에 속한 답변 기록을 가져온다.")
    @Transactional
    @Test
    void findByUserAndYearAndMonth() {
        // given

        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);

        // when
        List<Answer> answers = answerRepository.findByUserAndYearAndMonth(user, 2024, 11);

        // then
        assertThat(answers).hasSize(3);
        assertThat(answers)
                .allMatch(answer -> answer.getUser().equals(user));
    }
}
