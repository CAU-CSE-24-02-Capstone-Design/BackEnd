package Team02.BackEnd.converter;

import Team02.BackEnd.dto.InsightResponseDto.GetInsightDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsightConverter {
    public static GetInsightDto toGetInsightDto(final String insight) {
        return GetInsightDto.builder().
                insight(insight)
                .build();
    }
}
