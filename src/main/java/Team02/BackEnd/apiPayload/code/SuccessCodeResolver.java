package Team02.BackEnd.apiPayload.code;

import Team02.BackEnd.apiPayload.code.success.AnalysisSuccessCode;
import Team02.BackEnd.apiPayload.code.success.AnswerSuccessCode;
import Team02.BackEnd.apiPayload.code.success.CommonSuccessCode;
import Team02.BackEnd.apiPayload.code.success.FeedbackSuccessCode;
import Team02.BackEnd.apiPayload.code.success.InsightSuccessCode;
import Team02.BackEnd.apiPayload.code.success.QuestionSuccessCode;
import Team02.BackEnd.apiPayload.code.success.RecordSuccessCode;
import Team02.BackEnd.apiPayload.code.success.ReissueSuccessCode;
import Team02.BackEnd.apiPayload.code.success.SelfFeedbackSuccessCode;
import Team02.BackEnd.apiPayload.code.success.StatisticsSuccessCode;
import Team02.BackEnd.apiPayload.code.success.UserSuccessCode;
import java.util.Arrays;
import java.util.List;

public class SuccessCodeResolver {

    private static final List<Class<? extends SuccessCode>> SUCCESS_CODE_CLASSES = List.of(
            CommonSuccessCode.class,
            UserSuccessCode.class,
            ReissueSuccessCode.class,
            InsightSuccessCode.class,
            AnswerSuccessCode.class,
            RecordSuccessCode.class,
            QuestionSuccessCode.class,
            FeedbackSuccessCode.class,
            StatisticsSuccessCode.class,
            SelfFeedbackSuccessCode.class,
            AnalysisSuccessCode.class
    );

    public static SuccessCode fromCodeName(String successCodeName) {
        return SUCCESS_CODE_CLASSES.stream()
                .flatMap(enumClass -> Arrays.stream(enumClass.getEnumConstants())) // 각 enum 클래스의 상수를 순회
                .filter(code -> code.getName().equals(successCodeName)) // name()을 이용하여 상수 이름을 비교
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 SuccessCode가 없습니다 : " + successCodeName));
    }
}
