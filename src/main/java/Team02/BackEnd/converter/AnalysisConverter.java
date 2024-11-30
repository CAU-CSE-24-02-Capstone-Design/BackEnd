package Team02.BackEnd.converter;

import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisDto;

public class AnalysisConverter {
    public static GetAnalysisDto toGetAnalysisDto(String analysisText) {
        return GetAnalysisDto.builder()
                .analysisText(analysisText)
                .build();
    }
}
