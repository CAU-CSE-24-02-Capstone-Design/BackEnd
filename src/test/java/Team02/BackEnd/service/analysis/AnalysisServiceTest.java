package Team02.BackEnd.service.analysis;

import static Team02.BackEnd.util.TestUtil.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import Team02.BackEnd.converter.AnalysisConverter;
import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.repository.AnalysisRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class AnalysisServiceTest {

    @Mock
    private AnalysisRepository analysisRepository;

    @InjectMocks
    private AnalysisService analysisService;

    private User user;

    @BeforeEach
    void setUp() {
        user = createUser();
    }

    @DisplayName("사용자의 일주일치 분석 리포트 생성하기")
    @Test
    @WithMockUser(value = "tlsgusdn4818@gmail.com", roles = {"USER"})
    void saveAnalysis() {
        // given
        List<List<String>> analysisText = new ArrayList<>();
        Analysis analysis = AnalysisConverter.toAnalysis(analysisText, user);

        // when
        analysisService.saveAnalysis(analysisText, user);

        // then
        verify(analysisRepository, times(1)).save(any(Analysis.class));
    }
}