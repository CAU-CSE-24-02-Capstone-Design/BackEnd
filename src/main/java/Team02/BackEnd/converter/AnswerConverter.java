package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.oauth.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerConverter {
    public static Answer toAnswer(User user, Question question) {
        return Answer.builder()
                .question(question)
                .user(user)
                .evaluation(0)
                .build();
    }
}
