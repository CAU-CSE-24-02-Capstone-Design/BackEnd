package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.analysisDto.AnalysisRequestDto.GetComponentToMakeAnalysisDto;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisDto;
import java.util.List;

public class AnalysisConverter {
    public static GetAnalysisDto toGetAnalysisDto(String analysisText) {
        return GetAnalysisDto.builder()
                .analysisText(analysisText)
                .build();
    }

    public static GetComponentToMakeAnalysisDto toGetComponentToMakeAnalysisDto(List<String> questions,
                                                                                List<String> beforeScripts) {
        return GetComponentToMakeAnalysisDto.builder()
                .questions(questions)
                .beforeScripts(beforeScripts)
                .build();
    }

    public static Analysis toAnalysis(String analysisText, User user) {
        return Analysis.builder()
                .analysisText(analysisText)
                .user(user)
                .build();
    }
}
