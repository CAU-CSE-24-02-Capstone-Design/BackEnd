package Team02.BackEnd.repository;

import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
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
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("질문 번호에 맞는 질문 찾기.")
    @Test
    void findByQuestionIndex() {
        // given
        Long level = 1L;
        User user = createUser();
        Question question = createQuestion();
        questionRepository.save(question);

        // when
        Question questionResult = questionRepository.findByQuestionIndexAndLevel(user.getQuestionNumber(level), level);

        // then
        assertEquals(question, questionResult);
    }
}
