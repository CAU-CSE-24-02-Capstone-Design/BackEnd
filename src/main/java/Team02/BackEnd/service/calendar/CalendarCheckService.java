package Team02.BackEnd.service.calendar;

import static Team02.BackEnd.constant.Constants.BASE_TIME_ZONE;
import static Team02.BackEnd.constant.Constants.NEW_TIME_ZONE;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.service.answer.AnswerCheckService;
import Team02.BackEnd.service.feedback.FeedbackCheckService;
import Team02.BackEnd.service.user.UserCheckService;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CalendarCheckService {

    private static final int MONTH_SIZE = 32;

    private final UserCheckService userCheckService;
    private final AnswerCheckService answerCheckService;
    private final FeedbackCheckService feedbackCheckService;

    @Transactional(readOnly = true)
    public Long[] getDatesWhenUserDid(final String accessToken, final String year, final String month) {
        User user = userCheckService.getUserByToken(accessToken);
        List<Answer> answersInPeriod = answerCheckService.findAnswersByUserAndYearAndMonth(user, year, month);
        log.info("사용자의 특정 년, 월에 대한 스피치 기록 가져오기, userId : {}, year : {}, month : {}", user.getId(), year, month);
        return createAnswerIdDidThisPeriod(answersInPeriod);
    }

    private Long[] createAnswerIdDidThisPeriod(final List<Answer> answersInPeriod) {
        Long[] answerIdDidThisPeriod = Stream.generate(() -> 0L)
                .limit(MONTH_SIZE)
                .toArray(Long[]::new);

        answersInPeriod.stream()
                .filter(feedbackCheckService::isFeedbackExistsWithAnswer)
                .forEach(answer -> {
                    int day = answer.getCreatedAt()
                            .atZone(ZoneId.of(BASE_TIME_ZONE))
                            .withZoneSameInstant(ZoneId.of(NEW_TIME_ZONE))
                            .getDayOfMonth();
                    answerIdDidThisPeriod[day - 1] = answer.getId();
                });
        return answerIdDidThisPeriod;
    }
}
