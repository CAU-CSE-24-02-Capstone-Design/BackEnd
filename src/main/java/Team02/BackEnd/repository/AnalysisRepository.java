package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    @Query("SELECT a FROM Analysis a WHERE a.user.id = :userId ORDER BY a.createdAt DESC")
    Analysis findMostRecentAnalysisByUserId(@Param("userId") Long userId);
}
