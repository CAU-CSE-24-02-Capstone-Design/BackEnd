package Team02.BackEnd.repository;

import Team02.BackEnd.domain.SelfFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelfFeedbackRepository extends JpaRepository<SelfFeedback, Long> {
    SelfFeedback findByAnswerId(Long answerId);
}
