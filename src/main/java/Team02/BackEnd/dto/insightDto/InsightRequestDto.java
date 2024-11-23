package Team02.BackEnd.dto.insightDto;

import java.util.List;
import lombok.Getter;

public class InsightRequestDto {

    @Getter
    public static class GetInsightDto {
        List<String> insight;
    }
}
