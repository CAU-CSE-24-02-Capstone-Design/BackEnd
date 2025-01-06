package Team02.BackEnd.service.answer;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnswerHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.answerDto.AnswerDto;
import Team02.BackEnd.dto.answerDto.AnswerDto.AnswerQuestionDto;
import Team02.BackEnd.repository.AnswerRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnswerCheckService {

    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public List<Answer> getAnswersByUserId(final Long userId) {
        List<Answer> answers = answerRepository.findByUserId(userId);
        validateAnswersEmpty(answers);
        return answers;
    }

    @Transactional(readOnly = true)
    public List<Long> getAnswerIdsByUserId(final Long userId) {
        List<Long> answers = answerRepository.findAnswerIdsByUserId(userId);
        validateAnswersEmpty(answers);
        return answers;
    }

    @Transactional(readOnly = true)
    public List<AnswerDto.AnswerIdDto> getAnswerIdDtosByUserId(final Long userId) {
        List<AnswerDto.AnswerIdDto> answerDatas = answerRepository.findAnswerIdByUserId(userId);
        validateAnswersEmpty(answerDatas);
        return answerDatas;
    }

    @Transactional(readOnly = true)
    public List<AnswerDto.AnswerIdDto> getLatestAnswerIdDtosByUserIdWithSize(final Long userId, final int size) {
        Pageable pageable = PageRequest.of(0, size);
        List<AnswerDto.AnswerIdDto> answerDatas = answerRepository.findLatestAnswerIdByUserIdWithSize(userId, pageable);
        validateAnswersEmpty(answerDatas);
        return answerDatas;
    }

    @Transactional(readOnly = true)
    public List<AnswerDto.AnswerLevelDto> getAnswerLevelDtosWithLevelByUserId(final Long userId) {
        List<AnswerDto.AnswerLevelDto> answerDatas = answerRepository.findAnswersWithLevelByUserId(userId);
        validateAnswersEmpty(answerDatas);
        return answerDatas;
    }

    @Transactional(readOnly = true)
    public Answer getAnswerByAnswerId(final Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        validateAnswerIsNotNull(answer);
        return answer;
    }

    @Transactional(readOnly = true)
    public List<AnswerDto.AnswerIdDto> findAnswersByUserAndYearAndMonth(final Long userId, final String year,
                                                                        final String month) {
        List<AnswerDto.AnswerIdDto> answers = answerRepository.findByUserAndYearAndMonth(userId, Integer.parseInt(year),
                Integer.parseInt(month));
        validateAnswersEmpty(answers);
        return answers;
    }

    @Transactional(readOnly = true)
    public Optional<Answer> getLatestAnswerByUserId(final Long userId) {
        Pageable pageable = PageRequest.of(0, 1);
        return answerRepository.findLatestAnswerByUserId(userId, pageable).stream().findFirst();
    }

    @Transactional(readOnly = true)
    public List<Long> getAnswerIdsByUserWithSize(final Long userId, final int size) {
        Pageable pageable = PageRequest.of(0, size);
        return answerRepository.findLatestAnswerIdByUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    public List<Answer> getAnswerByUserIdWithSize(final Long userId, final int size) {
        Pageable pageable = PageRequest.of(0, size);
        return answerRepository.findLatestAnswerByUserId(userId, pageable);
    }

    @Transactional(readOnly = true)
    public Boolean checkSpeechLevel(final Long questionLevel, final Long level) {
        return Objects.equals(questionLevel, level);
    }

    @Transactional(readOnly = true)
    public List<String> findQuestionDescriptionsByUser(final User user, final int number) {
        Pageable pageable = PageRequest.of(0, number);
        List<AnswerDto.AnswerQuestionDto> answerQuestionDtos = answerRepository.findLatestAnswersWithQuestionByUserId(
                user.getId(), pageable);
        user.updateAnalyzeCompleteAnswerIndex(answerQuestionDtos.get(0).getId());
        return answerQuestionDtos.stream()
                .map(AnswerQuestionDto::getDescription)
                .toList();
    }

    private void validateAnswerIsNotNull(final Answer answer) {
        if (answer == null) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
    }

    private <T> void validateAnswersEmpty(final List<T> answers) {
        if (answers.isEmpty()) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
    }
}
