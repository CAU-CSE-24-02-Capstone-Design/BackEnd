package Team02.BackEnd.dto.analysisDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AnalysisRequestDto {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetComponentToMakeAnalysisDto {
        List<String> questions;
        List<String> beforeScripts;
    }
}
