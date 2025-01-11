package Team02.BackEnd.service.answer;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.answerDto.AnswerDto;
import Team02.BackEnd.dto.answerDto.AnswerDto.AnswerQuestionDto;
import Team02.BackEnd.repository.AnswerRepository;
import Team02.BackEnd.validator.AnswerValidator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public class AnswerCheckService {

    private final AnswerValidator answerValidator;
    private final AnswerRepository answerRepository;

    public List<Answer> getAnswersByUserId(final Long userId) {
        List<Answer> answers = answerRepository.findByUserId(userId);
        answerValidator.validateAnswersEmpty(answers);
        return answers;
    }

    public Answer getAnswerByAnswerId(final Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        answerValidator.validateAnswer(answer);
        return answer;
    }

    public List<Long> getAnswerIdsByUserId(final Long userId) {
        List<Long> answers = answerRepository.findAnswerIdsByUserId(userId);
        answerValidator.validateAnswersEmpty(answers);
        return answers;
    }

    public List<Long> getAnswerIdsByUserIdWithSize(final Long userId, final int size) {
        Pageable pageable = PageRequest.of(0, size);
        return answerRepository.findLatestAnswerIdByUserId(userId, pageable);
    }

    public List<AnswerDto.AnswerIdDto> getAnswerIdDtosByUserId(final Long userId) {
        List<AnswerDto.AnswerIdDto> answerDatas = answerRepository.findAnswerIdByUserId(userId);
        answerValidator.validateAnswersEmpty(answerDatas);
        return answerDatas;
    }

    public List<AnswerDto.AnswerIdDto> getLatestAnswerIdDtosByUserIdWithSize(final Long userId, final int size) {
        Pageable pageable = PageRequest.of(0, size);
        List<AnswerDto.AnswerIdDto> answerDatas = answerRepository.findLatestAnswerIdByUserIdWithSize(userId, pageable);
        answerValidator.validateAnswersEmpty(answerDatas);
        return answerDatas;
    }

    public List<AnswerDto.AnswerLevelDto> getAnswerLevelDtosWithLevelByUserId(final Long userId) {
        List<AnswerDto.AnswerLevelDto> answerDatas = answerRepository.findAnswersWithLevelByUserId(userId);
        answerValidator.validateAnswersEmpty(answerDatas);
        return answerDatas;
    }

    public List<AnswerDto.AnswerIdDto> findAnswerIdDtosByUserAndYearAndMonth(final Long userId, final String year,
                                                                             final String month) {
        List<AnswerDto.AnswerIdDto> answers = answerRepository.findByUserAndYearAndMonth(userId, Integer.parseInt(year),
                Integer.parseInt(month));
        answerValidator.validateAnswersEmpty(answers);
        return answers;
    }

    public Optional<Answer> getLatestAnswerByUserId(final Long userId) {
        Pageable pageable = PageRequest.of(0, 1);
        return answerRepository.findLatestAnswerByUserId(userId, pageable).stream().findFirst();
    }

    public Boolean checkSpeechLevel(final Long questionLevel, final Long level) {
        return Objects.equals(questionLevel, level);
    }

    public List<String> findQuestionDescriptionsByUser(final User user, final int number) {
        Pageable pageable = PageRequest.of(0, number);
        List<AnswerDto.AnswerQuestionDto> answerQuestionDtos = answerRepository.findLatestAnswersWithQuestionByUserId(
                user.getId(), pageable);
        user.updateAnalyzeCompleteAnswerIndex(answerQuestionDtos.get(0).getId());
        return answerQuestionDtos.stream()
                .map(AnswerQuestionDto::getDescription)
                .toList();
    }
}
