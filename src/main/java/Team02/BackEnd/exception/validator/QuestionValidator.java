package Team02.BackEnd.exception.validator;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.QuestionHandler;
import Team02.BackEnd.domain.Question;

public class QuestionValidator {

    public static void validateQuestionIsNotNull(Question question) {
        if (question == null) {
            throw new QuestionHandler(ErrorStatus._QUESTION_NOT_FOUND);
        }
    }
}
