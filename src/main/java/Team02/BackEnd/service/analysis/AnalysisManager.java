package Team02.BackEnd.service.analysis;

import Team02.BackEnd.dto.analysisDto.AnalysisApiDataDto;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisDto;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisFromFastApiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnalysisManager {

    private final AnalysisService analysisService;
    private final AnalysisCheckService analysisCheckService;
    private final AnalysisApiService analysisApiService;

    public void saveAnalysis(final String accessToken) {
        AnalysisApiDataDto relatedData = analysisCheckService.getDataForAnalysisApi(accessToken);
        GetAnalysisFromFastApiDto response = analysisApiService.getAnalysisFromFastApi(accessToken,
                relatedData.questions(),
                relatedData.beforeScripts());
        analysisService.saveAnalysis(response.getAnalysisText(), relatedData.user());
    }

    public boolean canSaveAnalysis(final String accessToken) {
        return analysisCheckService.canSaveAnalysis(accessToken);
    }

    public GetAnalysisDto getAnalysis(final String accessToken) {
        return analysisCheckService.getAnalysis(accessToken);
    }
}
