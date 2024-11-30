package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Statistics;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    Optional<Statistics> findByAnswerId(final Long answerId);
}
