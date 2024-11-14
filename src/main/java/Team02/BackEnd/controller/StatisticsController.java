package Team02.BackEnd.controller;

import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_PREFIX;
import static Team02.BackEnd.constant.Constants.ACCESS_TOKEN_REPLACEMENT;

import Team02.BackEnd.apiPayload.ApiResponse;
import Team02.BackEnd.apiPayload.code.status.SuccessStatus;
import Team02.BackEnd.dto.StatisticsRequestDto;
import Team02.BackEnd.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PostMapping("/statistics")
    @Operation(summary = "fast api -> spring", description = "통계(간투어 등 횟수) 디비 저장용 api, 유저 토큰 같이 넘겨주기")
    public ApiResponse<Void> saveStatistics(@RequestBody final StatisticsRequestDto.GetStatisticsDto request,
                                            @RequestHeader("Authorization") final String authorization) {
        String token = authorization.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        statisticsService.saveStatistics(request, token);
        return ApiResponse.ofNoting(SuccessStatus.SAVE_STATISTICS);
    }

    @GetMapping("/statistics")
    @Operation(
            summary = "통계 데이터 불러오기 react -> spring",
            description = """
                        **filter**
                        - filter=gantour
                        - filter=silent
                        - filter=word
                        - filter=context
                            
                    헤더에 토큰 
                    """
    )
    public ApiResponse<HashMap<String, String>> getFilterStatistics(@RequestParam("filter") final String filter,
                                                                    @RequestHeader("authorization") final String authorization) {
        /**
         * 필터 확인하고 statistics에서 지금 로그인 된 user 데이터만 created at 기준으로 정렬해서 가져옴
         */
        String token = authorization.replace(ACCESS_TOKEN_PREFIX, ACCESS_TOKEN_REPLACEMENT);
        return ApiResponse.of(SuccessStatus.GET_STATISTICS, statisticsService.getFilterStatistics(filter, token));

    }

}
