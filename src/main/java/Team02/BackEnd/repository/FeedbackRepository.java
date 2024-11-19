package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Feedback;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findByAnswerId(final Long answerId);

    Page<Feedback> findByUserId(final Long userId, final PageRequest pageRequest);

    List<Feedback> findAllByUserId(final Long userId);
}
