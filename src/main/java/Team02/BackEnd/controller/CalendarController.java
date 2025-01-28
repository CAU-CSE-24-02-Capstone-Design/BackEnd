package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.service.calendar.CalendarManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class CalendarController {

    private final CalendarManager calendarManager;

    @GetMapping("/calendars")
    @Operation(summary = "달력 데이터", description = "쿼리 파라미터로 년,월 제공 => getDatesWhenUserId[답변 한 날짜] = answerId, 답변 안한 날짜는 0")
    public ApiResponse<Long[]> getDatesWhenUserDid(@RequestHeader("Authorization") final String authorizationHeader,
                                                   @RequestParam("year") final String year,
                                                   @RequestParam("month") final String month) {
        String accessToken = authorizationHeader.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        return ApiResponse.of(SuccessStatus.GET_DATES_WHEN_USER_DID,
                calendarManager.getDatesWhenUserDid(accessToken, year, month));
    }
}
