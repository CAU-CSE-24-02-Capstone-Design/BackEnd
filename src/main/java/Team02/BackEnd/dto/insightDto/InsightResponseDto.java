package Team02.BackEnd.dto.insightDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class InsightResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetInsightDto {
        List<String> insight;
    }
}
