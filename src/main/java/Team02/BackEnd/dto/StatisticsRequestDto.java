package Team02.BackEnd.dto;

import lombok.Getter;

public class StatisticsRequestDto {

    @Getter
    public static class GetStatisticsDto {
        Long gantourCount; // 간투어 횟수
        Long silentTime; // 침묵 시간 초
        Long wrongWordCount; // 잘못된 단어 사용 횟수
        Long contextCount; // 잘못된 문맥 횟수

    }
}
