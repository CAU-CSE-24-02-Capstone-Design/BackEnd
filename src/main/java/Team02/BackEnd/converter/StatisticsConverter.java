package Team02.BackEnd.converter;

import static Team02.BackEnd.constant.Constants.BASE_TIME_ZONE;
import static Team02.BackEnd.constant.Constants.NEW_TIME_ZONE;

import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto;
import Team02.BackEnd.dto.statisticsDto.StatisticsResponseDto.GetAnalysisDto;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticsConverter {

    public static StatisticsResponseDto.GetStatisticsDto toGetStatisticsDto(final Statistics statistics) {
        return StatisticsResponseDto.GetStatisticsDto.builder()
                .day(statistics.getCreatedAt().atZone(ZoneId.of(BASE_TIME_ZONE))
                        .withZoneSameInstant(ZoneId.of(NEW_TIME_ZONE)).toLocalDate())
                .gantourCount(statistics.getGantourCount())
                .silentTime(statistics.getSilentTime())
                .build();
    }

    public static GetAnalysisDto toGetAnalysisDto(String analysisText) {
        return GetAnalysisDto.builder()
                .analysisText(analysisText)
                .build();
    }
}
