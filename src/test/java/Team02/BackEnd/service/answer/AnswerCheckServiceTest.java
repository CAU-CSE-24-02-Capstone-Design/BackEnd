package Team02.BackEnd.service.answer;

import static Team02.BackEnd.util.TestUtil.createAnswer;
import static Team02.BackEnd.util.TestUtil.createQuestion;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.answerDto.AnswerDto.AnswerIdDto;
import Team02.BackEnd.dto.answerDto.AnswerDto.AnswerQuestionDto;
import Team02.BackEnd.repository.AnswerRepository;
import Team02.BackEnd.validator.AnswerValidator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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
    @Mock
    private AnswerValidator answerValidator;

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
        user = createUser(1L);
        question = createQuestion(1L, "description");
        question1 = createQuestion(2L, "description1");
        question2 = createQuestion(3L, "description2");
        answer = createAnswer(1L, user, question);
        answer1 = createAnswer(2L, user, question1);
        answer2 = createAnswer(3L, user, question2);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnswersByUserId() {
        // given

        // when
        given(answerRepository.findByUserId(user.getId())).willReturn(List.of(answer1, answer2));
        List<Answer> answers = answerCheckService.getAnswersByUserId(user.getId());

        // then
        verify(answerValidator, times(1)).validateAnswersEmpty(List.of(answer1, answer2));
        assertThat(answers.size()).isEqualTo(2);
    }

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

//    @DisplayName("answerId에 맞는 answer 객체가 없으면 _ANSWER_NOT_FOUND 에러를 반환한다.")
//    @Test
//    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
//    void getNoAnswerByAnswerId() {
//        // given
//
//        // when
//        AnswerHandler exception = assertThrows(AnswerHandler.class, () -> {
//            answerCheckService.getAnswerByAnswerId(1L);
//        });
//
//        // then
//        assertThat(ErrorStatus._ANSWER_NOT_FOUND.getCode()).isEqualTo(exception.getCode().getReason().getCode());
//
//    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnswerIdsByUserId() {
        // given

        // when
        given(answerRepository.findAnswerIdsByUserId(user.getId())).willReturn(
                List.of(answer.getId(), answer1.getId(), answer2.getId()));

        List<Long> answerIds = answerCheckService.getAnswerIdsByUserId(user.getId());

        // then
        assertThat(answerIds.size()).isEqualTo(3);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnswerIdsByUserIdWithSize() {
        // given
        Pageable pageable = PageRequest.of(0, 1);

        // when
        given(answerRepository.findLatestAnswerIdByUserIdWithSize(user.getId(), pageable)).willReturn(
                List.of(answer.getId()));

        List<Long> answerIds = answerCheckService.getAnswerIdsByUserIdWithSize(user.getId(), 1);

        // then
        assertThat(answerIds.size()).isEqualTo(1);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnswerIdDtosByUserId() {
        // given
        AnswerIdDto answerIdDto = AnswerIdDto.builder()
                .id(answer.getId())
                .createdAt(answer.getCreatedAt())
                .build();

        // when
        given(answerRepository.findAnswerIdDtosByUserId(user.getId())).willReturn(List.of(answerIdDto));

        List<AnswerIdDto> answerIdDtos = answerCheckService.getAnswerIdDtosByUserId(user.getId());

        // then
        assertThat(answerIdDtos.size()).isEqualTo(1);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getLatestAnswerIdDtosByUserIdWithSize() {
        // given
        Pageable pageable = PageRequest.of(0, 1);
        AnswerIdDto answerIdDto = AnswerIdDto.builder()
                .id(answer.getId())
                .createdAt(answer.getCreatedAt())
                .build();

        // when
        given(answerRepository.findLatestAnswerIdDtosByUserIdWithSize(user.getId(), pageable)).willReturn(
                List.of(answerIdDto));

        List<AnswerIdDto> answerIdDtos = answerCheckService.getLatestAnswerIdDtosByUserIdWithSize(user.getId(), 1);

        // then
        assertThat(answerIdDtos.size()).isEqualTo(1);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getAnswerIdDtosWithLevelByUserId() {
        // given
        Long level = 1L;
        AnswerIdDto answerIdDto = AnswerIdDto.builder()
                .id(answer.getId())
                .createdAt(answer.getCreatedAt())
                .build();

        // when
        given(answerRepository.findAnswerIdDtosWithLevelByUserId(user.getId(), level)).willReturn(List.of(answerIdDto));

        List<AnswerIdDto> answerIdDtos = answerCheckService.getAnswerIdDtosWithLevelByUserId(user.getId(), level);

        // then
        assertThat(answerIdDtos.size()).isEqualTo(1);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void findAnswerIdDtosByUserAndYearAndMonth() {
        // given
        String year = "2025";
        String month = "1";

        AnswerIdDto answerIdDto = AnswerIdDto.builder()
                .id(answer.getId())
                .createdAt(answer.getCreatedAt())
                .build();

        // when
        given(answerRepository.findAnswerIdDtosByUserAndYearAndMonth(user.getId(), Integer.parseInt(year),
                Integer.parseInt(month))).willReturn(List.of(answerIdDto));

        List<AnswerIdDto> answerIdDtos = answerCheckService.findAnswerIdDtosByUserAndYearAndMonth(user.getId(), year,
                month);

        // then
        assertThat(answerIdDtos.size()).isEqualTo(1);
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void getLatestAnswerIdByUserId() {
        // given
        Pageable pageable = PageRequest.of(0, 1);

        // when
        given(answerRepository.findLatestAnswerIdByUserIdWithSize(user.getId(), pageable)).willReturn(
                List.of(answer.getId()));

        Optional<Long> answerId = answerCheckService.getLatestAnswerIdByUserId(user.getId());

        // then
        assertThat(answerId.isPresent()).isTrue();
        assertThat(answerId.get()).isEqualTo(answer.getId());
    }

    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void findQuestionDescriptionsByUser() {
        // given
        Pageable pageable = PageRequest.of(0, 1);

        AnswerQuestionDto answerQuestionDto = AnswerQuestionDto.builder()
                .id(answer.getId())
                .description(question.getDescription())
                .build();

        // when
        given(answerRepository.findLatestAnswerQuestionDtosByUserIdWithSize(user.getId(), pageable)).willReturn(
                List.of(answerQuestionDto));

        List<String> descriptions = answerCheckService.findQuestionDescriptionsByUser(user, 1);

        // then
        assertThat(descriptions.size()).isEqualTo(1);
        assertThat(descriptions.get(0)).isEqualTo(question.getDescription());
    }
}