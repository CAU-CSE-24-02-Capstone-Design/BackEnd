package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.QuestionRepository;
import Team02.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public Question getQuestionDescription(String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null){
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);
        }

        Question question = questionRepository.findByQuestionIndex(user.getQuestionNumber());

        user.setQuestionNumber(user.getQuestionNumber() + 1);

        return question;
    }
}
