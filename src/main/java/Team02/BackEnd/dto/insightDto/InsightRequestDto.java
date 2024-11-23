package Team02.BackEnd.dto.insightDto;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class InsightRequestDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetInsightDto {
        List<String> insight;

        public List<String> getInsight() {
            return Collections.unmodifiableList(insight);
        }
    }
}
