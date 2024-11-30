package Team02.BackEnd.dto.statisticsDto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StatisticsResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetStatisticsDto {
        LocalDate day;
        Long gantourCount;
        Double silentTime;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAnalysisDto {
        String analysisText;
    }
}
