package Team02.BackEnd.dto.statisticsDto;

import lombok.Getter;

public class StatisticsRequestDto {

    @Getter
    public static class GetStatisticsDto {
        Long gantourCount; // 간투어 횟수
        Double silentTime; // 침묵 시간 초
        Long answerId;
    }
}
