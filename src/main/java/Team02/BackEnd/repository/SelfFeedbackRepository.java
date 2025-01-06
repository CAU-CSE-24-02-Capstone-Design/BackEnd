package Team02.BackEnd.repository;

import Team02.BackEnd.domain.SelfFeedback;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SelfFeedbackRepository extends JpaRepository<SelfFeedback, Long> {

    @Query("SELECT COUNT(s) > 0 FROM SelfFeedback s WHERE s.answer.id = :answerId")
    boolean existsByAnswerId(@Param("answerId") final Long answerId);

    SelfFeedback findByAnswerId(final Long answerId);
}
