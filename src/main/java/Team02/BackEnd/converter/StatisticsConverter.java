package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticsConverter {

    private static final String BASE_TIME_ZONE = "UTC";
    private static final String NEW_TIME_ZONE = "Asia/Seoul";

    public static StatisticsResponseDto.GetStatisticsDto toGetStatisticsDto(final Statistics statistics) {
        return StatisticsResponseDto.GetStatisticsDto.builder()
                .day(statistics.getCreatedAt().atZone(ZoneId.of(BASE_TIME_ZONE))
                        .withZoneSameInstant(ZoneId.of(NEW_TIME_ZONE)).toLocalDate())
                .gantourCount(statistics.getGantourCount())
                .silentTime(statistics.getSilentTime())
                .build();
    }
}
