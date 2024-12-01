package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUserId(final Long userId);

    @Query("SELECT a FROM Answer a WHERE a.user = :user ORDER BY a.createdAt DESC")
    List<Answer> findLatestAnswerByUser(@Param("user") final User user, final Pageable pageable);

    @Query("SELECT a FROM Answer a WHERE a.user = :user AND YEAR(a.createdAt) = :year AND MONTH(a.createdAt) = :month ORDER BY a.createdAt ASC")
    List<Answer> findByUserAndYearAndMonth(@Param("user") final User user,
                                           @Param("year") final int year,
                                           @Param("month") final int month);

    @Query("SELECT q.description FROM Answer a JOIN a.question q WHERE a.user = :user ORDER BY a.createdAt ASC")
    List<String> findQuestionDescriptionsByUser(@Param("user") final User user, final Pageable pageable);
}
