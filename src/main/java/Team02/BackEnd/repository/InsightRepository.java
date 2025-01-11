package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Insight;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InsightRepository extends JpaRepository<Insight, Long> {

    @Query("SELECT i.insight FROM Insight i WHERE i.answer.id = :answerId")
    List<String> findInsightsByAnswerId(@Param("answerId") final Long answerId);
}
