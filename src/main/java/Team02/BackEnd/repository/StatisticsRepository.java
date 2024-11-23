package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    Statistics findByAnswerId(final Long answerId);
}
