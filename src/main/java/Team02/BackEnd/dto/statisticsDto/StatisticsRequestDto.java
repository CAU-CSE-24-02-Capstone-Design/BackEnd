package Team02.BackEnd.dto.statisticsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StatisticsRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetStatisticsDto {
        Long gantourCount; // 간투어 횟수
        Double silentTime; // 침묵 시간 초
        Long answerId;
    }
}
