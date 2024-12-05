package Team02.BackEnd.converter;

import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.analysisDto.AnalysisRequestDto.GetComponentToMakeAnalysisDto;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAnalysisDto;
import Team02.BackEnd.dto.analysisDto.AnalysisResponseDto.GetAvailabilityAnalysisDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalysisConverter {
    public static GetAnalysisDto toGetAnalysisDto(final List<List<String>> analysisText,
                                                  final List<String> answerDates) {
        return GetAnalysisDto.builder()
                .lastDate(answerDates.get(0))
                .firstDate(answerDates.get(answerDates.size() - 1))
                .analysisText(analysisText)
                .build();
    }

    public static GetComponentToMakeAnalysisDto toGetComponentToMakeAnalysisDto(final List<String> questions,
                                                                                final List<String> beforeScripts) {
        return GetComponentToMakeAnalysisDto.builder()
                .questions(questions)
                .beforeScripts(beforeScripts)
                .build();
    }

    public static Analysis toAnalysis(final List<List<String>> analysisText, final User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // List<List<String>> -> JSON String 변환
            String analysisTextJson = objectMapper.writeValueAsString(analysisText);
            return Analysis.builder()
                    .analysisText(analysisTextJson)
                    .user(user)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert analysisText to JSON", e);
        }
    }

    public static GetAvailabilityAnalysisDto toGetAvailabilityAnalysisDto(final boolean canSaveAnalysis) {
        return GetAvailabilityAnalysisDto.builder()
                .canSaveAnalysis(canSaveAnalysis)
                .build();
    }
}
