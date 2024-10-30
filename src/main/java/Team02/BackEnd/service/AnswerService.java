package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.converter.AnswerConverter;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.AnswerRepository;
import Team02.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    public Long getAnswerId(String accessToken, Question question) {
        String email = jwtService.extractEmail(accessToken).orElse(null);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null){
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);
        }

        Answer answer = AnswerConverter.toAnswer(user, question);
        answerRepository.save(answer);

        return answer.getId();
    }
}
