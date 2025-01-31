package Team02.BackEnd.converter;

import static Team02.BackEnd.constant.Constants.BASE_TIME_ZONE;
import static Team02.BackEnd.constant.Constants.NEW_TIME_ZONE;

import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.dto.statisticsDto.StatisticsDto.StatisticsDataDto;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticsConverter {

    public static StatisticsResponseDto.GetStatisticsDto toGetStatisticsDto(final StatisticsDataDto statisticsDataDto,
                                                                            final LocalDateTime createdAt) {
        return StatisticsResponseDto.GetStatisticsDto.builder()
                .day(createdAt.atZone(ZoneId.of(BASE_TIME_ZONE))
                        .withZoneSameInstant(ZoneId.of(NEW_TIME_ZONE)).toLocalDate())
                .gantourCount(statisticsDataDto.getGantourCount())
                .silentTime(statisticsDataDto.getSilentTime())
                .build();
    }
}
