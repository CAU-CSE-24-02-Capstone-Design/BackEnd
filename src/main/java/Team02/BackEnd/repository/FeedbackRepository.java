package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Feedback;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findByAnswerId(final Long answerId);

    @Query("SELECT COUNT(f) > 0 FROM Feedback f WHERE f.answer.id = :answerId")
    boolean existsByAnswerId(@Param("answerId") final Long answerId);

    @Query("SELECT f.createdAt FROM Feedback f WHERE f.answer.id = :answerId")
    LocalDateTime findCreatedAtByAnswerId(@Param("answerId") final Long answerId);

    @Query("SELECT f.beforeAudioLink FROM Feedback f WHERE f.user.id = :userId ORDER BY f.createdAt DESC")
    List<String> findLatestBeforeAudioLinksByUserIdWithSize(@Param("userId") final Long userId,
                                                         final Pageable pageable);

    @Query("SELECT f.beforeAudioLink FROM Feedback f WHERE f.user.id = :userId")
    List<String> findAllBeforeAudioLinksByUserId(@Param("userId") final Long userId);

    @Query("SELECT f.beforeScript FROM Feedback f WHERE f.user.id = :userId ORDER BY f.createdAt DESC")
    List<String> findLatestBeforeScriptsByUserIdWithSize(@Param("userId") final Long userId, final Pageable pageable);
}
