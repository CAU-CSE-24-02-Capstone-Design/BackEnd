package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Insight;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsightRepository extends JpaRepository<Insight, Long> {

    List<Insight> findAllByAnswerId(final Long answerId);
}
