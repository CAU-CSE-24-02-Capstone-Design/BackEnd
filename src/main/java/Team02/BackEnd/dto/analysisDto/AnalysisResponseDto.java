package Team02.BackEnd.dto.analysisDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AnalysisResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAnalysisDto {
        String analysisText;
    }
}
