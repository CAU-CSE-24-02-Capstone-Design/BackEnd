package Team02.BackEnd.repository;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.answerDto.AnswerDto.AnswerIdDto;
import Team02.BackEnd.dto.answerDto.AnswerDto.AnswerQuestionDto;
import jakarta.transaction.Transactional;
import java.util.List;
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
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    private User user;
    private Question question1;
    private Question question2;
    private Question question3;
    private Answer answer1;
    private Answer answer2;
    private Answer answer3;

    @BeforeEach
    void setUp() {
        user = createUser();
        question1 = createQuestion("description1");
        question2 = createQuestion("description2");
        question3 = createQuestion("description3");
        answer1 = createAnswer(user, question1);
        answer2 = createAnswer(user, question2);
        answer3 = createAnswer(user, question3);
    }

    @DisplayName("UserId로 모든 Answer를 가져온다.")
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

    @DisplayName("UserId로 모든 AnswerId를 가져온다.")
    @Transactional
    @Test
    void findAnswerIdsByUserId() {
        // given
        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);

        // when
        List<Long> answerIds = answerRepository.findAnswerIdsByUserId(user.getId());

        // then
        assertThat(answerIds).hasSize(3);
    }

    @DisplayName("UserId로 최근 AnswerId를 특정 개수만큼 가져온다.")
    @Transactional
    @Test
    void findAnswerIdsByUserIdWithSize() {
        // given
        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);

        // when
        Pageable pageable = PageRequest.of(0, 1);
        List<Long> answerIds = answerRepository.findLatestAnswerIdByUserIdWithSize(user.getId(), pageable);

        // then
        assertThat(answerIds).hasSize(1);
        assertThat(answerIds.get(0)).isEqualTo(answer3.getId());
    }

    @DisplayName("UserId로 모든 AnswerIdDto를 가져온다.")
    @Transactional
    @Test
    void findAnswerIdDtosByUserId() {
        // given
        answerRepository.save(answer1);
        answerRepository.save(answer2);

        // when
        List<AnswerIdDto> answerIdDtos = answerRepository.findAnswerIdDtosByUserId(user.getId());

        // then
        assertThat(answerIdDtos).hasSize(2);
    }

    @DisplayName("UserId로 최근 AnswerIdDto를 특정 개수만큼 가져온다")
    @Transactional
    @Test
    void findLatestAnswerIdDtosByUserIdWithSize() {
        // given
        answerRepository.save(answer1);
        answerRepository.save(answer2);

        // when
        Pageable pageable = PageRequest.of(0, 1);
        List<AnswerIdDto> answerIdDtos = answerRepository.findLatestAnswerIdDtosByUserIdWithSize(user.getId(),
                pageable);

        // then
        assertThat(answerIdDtos).hasSize(1);
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
        List<AnswerIdDto> answerIdDtos = answerRepository.findAnswerIdDtosByUserAndYearAndMonth(user.getId(), 2025, 1);

        // then
        assertThat(answerIdDtos).hasSize(3);
    }

    @DisplayName("UserId로 AnswerIdDto를 가져온다.")
    @Transactional
    @Test
    void findAnswerIdDtosWithLevelByUserId() {
        // given
        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);
        Long level = 1L;

        // when
        List<AnswerIdDto> answerIdDtos = answerRepository.findAnswerIdDtosWithLevelByUserId(user.getId(), level);

        // then
        assertThat(answerIdDtos).hasSize(3);
    }

    @DisplayName("UserId로 최근 AnswerId와 질문을 가져온다.")
    @Transactional
    @Test
    void findLatestAnswerQuestionDtosByUserIdWithSize() {
        // given
        Pageable pageable = PageRequest.of(0, 7);
        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);

        // then
        List<AnswerQuestionDto> answerQuestionDtos = answerRepository.findLatestAnswerQuestionDtosByUserIdWithSize(
                user.getId(), pageable);

        // when
        assertThat(answerQuestionDtos).hasSize(3);
    }
}
