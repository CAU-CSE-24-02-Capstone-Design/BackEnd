package Team02.BackEnd.apiPayload.code;

import Team02.BackEnd.apiPayload.code.error.AccessTokenErrorCode;
import Team02.BackEnd.apiPayload.code.error.AnalysisErrorCode;
import Team02.BackEnd.apiPayload.code.error.AnswerErrorCode;
import Team02.BackEnd.apiPayload.code.error.CommonErrorCode;
import Team02.BackEnd.apiPayload.code.error.FeedbackErrorCode;
import Team02.BackEnd.apiPayload.code.error.InsightErrorCode;
import Team02.BackEnd.apiPayload.code.error.QuestionErrorCode;
import Team02.BackEnd.apiPayload.code.error.RefreshTokenErrorCode;
import Team02.BackEnd.apiPayload.code.error.SelfFeedbackErrorCode;
import Team02.BackEnd.apiPayload.code.error.StatisticsErrorCode;
import Team02.BackEnd.apiPayload.code.error.UserErrorCode;
import java.util.Arrays;
import java.util.List;

public class ErrorCodeResolver {

    private static final List<Class<? extends ErrorCode>> ERROR_CODE_CLASSES = List.of(
            CommonErrorCode.class,
            AccessTokenErrorCode.class,
            AnalysisErrorCode.class,
            AnswerErrorCode.class,
            FeedbackErrorCode.class,
            InsightErrorCode.class,
            QuestionErrorCode.class,
            RefreshTokenErrorCode.class,
            SelfFeedbackErrorCode.class,
            StatisticsErrorCode.class,
            UserErrorCode.class
    );

    public static ErrorCode fromCodeName(String errorCodeName) {
        return ERROR_CODE_CLASSES.stream()
                .flatMap(enumClass -> Arrays.stream(enumClass.getEnumConstants())) // 각 enum 클래스의 상수를 순회
                .filter(code -> code.getName().equals(errorCodeName)) // name()을 이용하여 상수 이름을 비교
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 ErrorCode가 없습니다 : " + errorCodeName));
    }
}