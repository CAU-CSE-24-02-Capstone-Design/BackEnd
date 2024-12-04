package Team02.BackEnd.dto.analysisDto;

import jakarta.validation.constraints.NotNull;
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
        String firstDate;
        String lastDate;
        String analysisText;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAnalysisFromFastApiDto {
        @NotNull
        String analysisText;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAvailabilityAnalysisDto {
        boolean canSaveAnalysis;
    }
}
