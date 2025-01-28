package Team02.BackEnd.service.calendar;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalendarManager {

    public final CalendarCheckService calendarCheckService;

    public Long[] getDatesWhenUserDid(final String accessToken, final String year, final String month) {
        return calendarCheckService.getDatesWhenUserDid(accessToken, year, month);
    }
}
