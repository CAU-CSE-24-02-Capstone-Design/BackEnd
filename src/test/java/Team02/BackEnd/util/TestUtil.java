package Team02.BackEnd.util;

import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.Insight;
import Team02.BackEnd.domain.Question;
import Team02.BackEnd.domain.Role;
import Team02.BackEnd.domain.SelfFeedback;
import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.oauth.OauthServerType;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TestUtil {

    public static User createUser() {
        return User.builder()
                .email("tlsgusdn4818@gmail.com")
                .name("Hyun")
                .role(Role.USER)
                .oauthId(new OauthId("1", OauthServerType.GOOGLE))
                .voiceUrl("voiceUrl")
                .level1QuestionNumber(1L)
                .level2QuestionNumber(1L)
                .level3QuestionNumber(1L)
                .build();
    }

    public static Answer createAnswer(final User user, final Question question) {
        return Answer.builder()
                .user(user)
                .question(question)
                .evaluation(1)
                .createdAt(LocalDateTime.of(2024, 11, 20, 15, 30)
                        .atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
                .build();
    }

    public static Question createQuestion() {
        return Question.builder()
                .description("description")
                .questionIndex(1L)
                .level(1L)
                .build();
    }

    public static Feedback createFeedback(final User user, final Answer answer) {
        return Feedback.builder()
                .beforeAudioLink("ba")
                .beforeScript("bs")
                .afterAudioLink("aa")
                .afterScript("as")
                .feedbackText("ft")
                .user(user)
                .answer(answer)
                .createdAt(LocalDateTime.parse("2024-11-28T11:11:11"))
                .build();
    }

    public static Insight createInsight(final Answer answer) {
        return Insight.builder()
                .answer(answer)
                .insight("insight")
                .build();
    }

    public static SelfFeedback createSelfFeedback(final Answer answer) {
        return SelfFeedback.builder()
                .feedback("feedback")
                .answer(answer)
                .build();
    }

    public static Statistics createStatistics(final Answer answer) {
        return Statistics.builder()
                .gantourCount(1L)
                .silentTime(5.0)
                .createdAt(LocalDateTime.now())
                .answer(answer)
                .build();
    }

    public static Analysis createAnalysis(final User user) {
        return Analysis.builder()
                .analysisText("text")
                .user(user)
                .build();
    }
}
