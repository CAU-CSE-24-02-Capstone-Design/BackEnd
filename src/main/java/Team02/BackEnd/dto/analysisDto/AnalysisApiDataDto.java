package Team02.BackEnd.dto.analysisDto;

import Team02.BackEnd.domain.oauth.User;
import java.util.List;

public record AnalysisApiDataDto(User user, List<String> questions, List<String> beforeScripts) {
}
