package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Feedback;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findByAnswerId(Long answerId);

    Page<Feedback> findByUserId(Long userId, PageRequest pageRequest);

    List<Feedback> findAllByUserId(Long userId);
}
