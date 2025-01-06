package Team02.BackEnd.repository;

import static Team02.BackEnd.util.TestUtil.createAnalysis;
import static Team02.BackEnd.util.TestUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;

import Team02.BackEnd.domain.Analysis;
import Team02.BackEnd.domain.oauth.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AnalysisRepositoryTest {

    @Autowired
    private AnalysisRepository analysisRepository;

    @DisplayName("사용자의 가장 최근 분석 리포트 가져오기")
    @Transactional
    @Test
    void findMostRecentAnalysisByUserId() {
        // given
        User user = createUser();
        Analysis analysis = createAnalysis(user);
        analysisRepository.save(analysis);
        Pageable pageable = PageRequest.of(0, 1);

        // when
        List<Analysis> findAnalysis = analysisRepository.findMostRecentAnalysisByUserId(user.getId(), pageable);
        Optional<Analysis> findAnalysis1 = findAnalysis.stream().findFirst();

        // then
        assertThat(findAnalysis1.get().getAnalysisText()).isEqualTo(analysis.getAnalysisText());
        assertThat(findAnalysis1.get().getUser()).isEqualTo(user);
    }
}