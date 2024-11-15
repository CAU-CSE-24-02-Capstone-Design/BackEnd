package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AnswerHandler;
import Team02.BackEnd.converter.AnswerConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnswerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AnswerRepository answerRepository;
    private final UserService userService;

    public Long getAnswerId(final String accessToken, final Question question) {
        User user = userService.getUserByToken(accessToken);
        Answer answer = AnswerConverter.toAnswer(user, question);

        answerRepository.save(answer);
        return answer.getId();
    }

    public List<Answer> getAnswersByUserId(final Long userId) {
        List<Answer> answers = answerRepository.findByUserId(userId);
        answers.forEach(this::validateAnswerIsNotNull);
        return answers;
    }

    public Answer getLatestAnswerByUserId(final Long userId) {
        List<Answer> answers = answerRepository.findByUserId(userId);
        Answer answer = answers.stream()
                .max(Comparator.comparing(Answer::getCreatedAt))
                .orElse(null);
        validateAnswerIsNotNull(answer);
        return answer;
    }

    public Answer getAnswerByAnswerId(final Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        validateAnswerIsNotNull(answer);
        return answer;
    }

    public void saveAiInsight(final List<String> insight, final Long answerId) {
        try {
            Answer answer = getAnswerByAnswerId(answerId);
            answer.updateInsight(objectMapper.writeValueAsString(insight));
            answerRepository.save(answer);
        } catch (JsonProcessingException e) {
            throw new AnswerHandler(ErrorStatus._INSIGHT_INVALID_CONVERT);
        }
    }

    public List<String> getAiInsight(final Long answerId) {
        try {
            Answer answer = getAnswerByAnswerId(answerId);
            return objectMapper.readValue(answer.getInsight(), new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            throw new AnswerHandler(ErrorStatus._INSIGHT_INVALID_CONVERT);
        }
    }

    private void validateAnswerIsNotNull(final Answer answer) {
        if (answer == null) {
            throw new AnswerHandler(ErrorStatus._ANSWER_NOT_FOUND);
        }
    }
}
