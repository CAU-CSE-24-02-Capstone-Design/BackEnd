package Team02.BackEnd.service.answer;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnswerHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnswerRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class AnswerCheckServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerCheckService answerCheckService;

    private User user;
    private Question question;
    private Question question1;
    private Question question2;
    private Answer answer;
    private Answer answer1;
    private Answer answer2;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = createQuestion();
        question1 = createQuestion();
        question2 = createQuestion();
        answer = createAnswer(user, question);
        answer1 = createAnswer(user, question1);
        answer2 = createAnswer(user, question2);
    }

    @DisplayName("사용자가 지금까지 진행한 모든 스피치에 대한 answer를 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnswersByUser() {
        // given

        // when
        given(answerRepository.findByUserId(user.getId())).willReturn(List.of(answer1, answer2));
        List<Answer> answers = answerCheckService.getAnswersByUser(user);

        // then
        assertThat(answers.size()).isEqualTo(2);
    }

    @DisplayName("사용자가 지금까지 진행한 스피치가 없다면 _ANSWER_NOT_FOUND 에러를 반환한다.")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getNoAnswersByUser() {
        // given

        // when
        AnswerHandler exception = assertThrows(AnswerHandler.class, () -> {
            answerCheckService.getAnswersByUser(user);
        });

        // then
        assertThat(ErrorStatus._ANSWER_NOT_FOUND.getCode()).isEqualTo(exception.getCode().getReason().getCode());
    }

    @DisplayName("answerId에 맞는 answer 객체를 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnswerByAnswerId() {
        // given

        // when
        given(answerRepository.findById(user.getId())).willReturn(Optional.ofNullable(answer));
        Answer findAnswer = answerCheckService.getAnswerByAnswerId(answer.getId());

        // then
        assertThat(findAnswer.getId()).isEqualTo(answer.getId());
    }

    @DisplayName("answerId에 맞는 answer 객체가 없으면 _ANSWER_NOT_FOUND 에러를 반환한다.")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getNoAnswerByAnswerId() {
        // given

        // when
        AnswerHandler exception = assertThrows(AnswerHandler.class, () -> {
            answerCheckService.getAnswerByAnswerId(1L);
        });

        // then
        assertThat(ErrorStatus._ANSWER_NOT_FOUND.getCode()).isEqualTo(exception.getCode().getReason().getCode());

    }

    @DisplayName("사용자의 해당 연, 월에 대한 Answer 엔티티를 전부 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void findAnswersByUserAndYearAndMonth() {
        // given
        String year = "2024";
        String month = "11";

        // when
        given(answerRepository.findByUserAndYearAndMonth(user, Integer.parseInt(year),
                Integer.parseInt(month))).willReturn(List.of(answer1, answer2));
        List<Answer> answers = answerCheckService.findAnswersByUserAndYearAndMonth(user, year, month);

        // then
        assertThat(answers.size()).isEqualTo(2);
        assertThat(answers.get(0)).isEqualTo(answer1);
        assertThat(answers.get(1)).isEqualTo(answer2);
    }

    @DisplayName("사용자의 해당 연, 월에 대한 Answer 엔티티가 없다면 _ANSWER_NOT_FOUND 에러를 반환한다.")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void findNoAnswersByUserAndYearAndMonth() {
        // given
        String year = "2023";
        String month = "10";

        // when
        given(answerRepository.findByUserAndYearAndMonth(user, Integer.parseInt(year),
                Integer.parseInt(month))).willReturn(Collections.emptyList());
        AnswerHandler exception = assertThrows(AnswerHandler.class, () -> {
            answerCheckService.findAnswersByUserAndYearAndMonth(user, year, month);
        });

        // then
        assertThat(ErrorStatus._ANSWER_NOT_FOUND.getCode()).isEqualTo(exception.getCode().getReason().getCode());
    }

    @DisplayName("스피치에 대한 자체 평가 점수를 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnswerEvaluation() {
        // given

        // when
        given(answerRepository.findById(user.getId())).willReturn(Optional.ofNullable(answer));
        int evaluation = answerCheckService.getAnswerEvaluation(answer.getId());

        // then
        assertThat(evaluation).isEqualTo(answer.getEvaluation());
    }

    @DisplayName("사용자의 가장 최근 스피치에 대한 Answer 엔티티를 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getLatestAnswerByUser() {
        // given

        // when
        given(answerRepository.findLatestAnswerByUser(user, PageRequest.of(0, 1))).willReturn(
                List.of(answer1, answer2));
        Optional<Answer> findAnswer = answerCheckService.getLatestAnswerByUser(user);

        // then
        assertTrue(findAnswer.isPresent());
        assertThat(findAnswer.get().getId()).isEqualTo(answer2.getId());
    }

    @DisplayName("사용자의 최근 몇 개의 스피치에 대한 Answer 엔티티를 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnswersByUserWithSize() {
        // given
        int size = 2;

        // when
        given(answerRepository.findLatestAnswerByUser(user, PageRequest.of(0, size))).willReturn(
                List.of(answer1, answer2));
        List<Answer> answers = answerCheckService.getAnswerByUserWithSize(user, size);

        // then
        assertThat(answers.size()).isEqualTo(2);
    }

    @DisplayName("스피치의 난이도를 비교한다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void checkSpeechLevel() {
        // given

        // when
        Boolean checkSpeechLevel = answerCheckService.checkSpeechLevel(answer, 1L);

        // then
        assertThat(checkSpeechLevel).isTrue();
    }

    @DisplayName("사용자의 이전 스피치 질문을 가져온다")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void findQuestionDescriptionsByUser() {
        // given
        int size = 7;
        Pageable pageable = PageRequest.of(0, size);
        List<Answer> answers = List.of(answer, answer1, answer2);
        List<String> descriptions = List.of(question.getDescription(), question1.getDescription(),
                question2.getDescription());

        // when
        given(answerRepository.findLatestAnswerByUser(user, pageable)).willReturn(answers);
        List<String> findDescriptions = answerCheckService.findQuestionDescriptionsByUser(user, size);

        // then
        assertThat(findDescriptions.size()).isEqualTo(descriptions.size());
    }
}