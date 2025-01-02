package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Feedback;
import Team02.BackEnd.domain.oauth.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT COUNT(f) > 0 FROM Feedback f WHERE f.answer.id =:answerId")
    boolean existsByAnswerId(final Long answerId);

    Optional<Feedback> findByAnswerId(final Long answerId);

    Page<Feedback> findByUserId(final Long userId, final PageRequest pageRequest);

    List<Feedback> findAllByUserId(final Long userId);

    @Query("SELECT f.beforeScript FROM Feedback f WHERE f.user = :user")
    List<String> findBeforeScriptByUser(@Param("user") User user, Pageable pageable);
}
