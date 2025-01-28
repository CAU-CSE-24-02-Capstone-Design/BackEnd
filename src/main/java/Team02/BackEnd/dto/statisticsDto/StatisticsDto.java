package Team02.BackEnd.dto.statisticsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StatisticsDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatisticsDataDto {
        private Long gantourCount;
        private Double silentTime;
    }
}
