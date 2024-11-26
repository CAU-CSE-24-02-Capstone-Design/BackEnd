package Team02.BackEnd.service;

import static Team02.BackEnd.constant.Constants.BASE_TIME_ZONE;
import static Team02.BackEnd.constant.Constants.NEW_TIME_ZONE;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CalendarService {

    private static final int MONTH_SIZE = 32;

    private final UserService userService;
    private final AnswerService answerService;
    private final FeedbackService feedbackService;

    public Long[] getDatesWhenUserDid(final String accessToken, final String year, final String month) {
        User user = userService.getUserByToken(accessToken);

        Long[] answerIdDidThisPeriod = Stream.generate(() -> 0L).
                limit(MONTH_SIZE).
                toArray(Long[]::new);
        List<Answer> answersInPeriod = answerService.findByUserAndYearAndMonth(user, year, month);

        answersInPeriod.stream()
                .filter(feedbackService::isFeedbackExistsWithAnswer)
                .forEach(answer -> {
                    int day = answer.getCreatedAt()
                            .atZone(ZoneId.of(BASE_TIME_ZONE))
                            .withZoneSameInstant(ZoneId.of(NEW_TIME_ZONE))
                            .getDayOfMonth();
                    answerIdDidThisPeriod[day - 1] = answer.getId();
                });

        log.info("사용자의 특정 년, 월에 대한 스피치 기록 가져오기, userId : {}, year : {}, month : {}", user.getId(), year, month);
        return answerIdDidThisPeriod;
    }
}
