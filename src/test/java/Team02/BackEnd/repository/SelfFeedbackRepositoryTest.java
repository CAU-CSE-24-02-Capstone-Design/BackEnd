package Team02.BackEnd.repository;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createSelfFeedback;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
import jakarta.transaction.Transactional;
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
class SelfFeedbackRepositoryTest {

    @Autowired
    private SelfFeedbackRepository selfFeedbackRepository;

    private User user;
    private Question question;
    private Answer answer;
    private SelfFeedback selfFeedback;

    @BeforeEach
    void setUp(){
        user = createUser();
        question = createQuestion();
        answer = createAnswer(user, question);
        selfFeedback = createSelfFeedback(answer);
    }

    @DisplayName("답변 기록에 대한 셀프 피드백 찾아오기")
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
}
