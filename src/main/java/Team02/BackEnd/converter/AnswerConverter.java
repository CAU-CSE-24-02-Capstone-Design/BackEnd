package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;

public class AnswerConverter {
    public static Answer toAnswer(User user, Question question) {
        return Answer.builder()
                .question(question)
                .user(user)
                .build();
    }
}
