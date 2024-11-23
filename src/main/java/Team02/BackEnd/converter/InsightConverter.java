package Team02.BackEnd.converter;

import Team02.BackEnd.dto.insightDto.InsightResponseDto.GetInsightDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsightConverter {
    public static GetInsightDto toGetInsightDto(final List<String> insight) {
        return GetInsightDto.builder()
                .insight(insight)
                .build();
    }
}
