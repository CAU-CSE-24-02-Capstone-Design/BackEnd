package Team02.BackEnd.dto.analysisDto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
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
        List<List<String>> analysisText;
        String firstDate;
        String lastDate;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAnalysisFromFastApiDto {
        @NotNull
        List<List<String>> analysisText;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAvailabilityAnalysisDto {
        boolean canSaveAnalysis;
    }
}
