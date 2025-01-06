package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Statistics;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    @Query("SELECT COUNT(s) > 0 FROM Statistics s WHERE s.answer.id = :answerId")
    boolean existsByAnswerId(@Param("answerId") Long answerId);

    Optional<Statistics> findByAnswerId(final Long answerId);
}
