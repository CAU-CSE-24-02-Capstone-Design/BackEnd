package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUserId(final Long userId);

    @Query("SELECT a FROM Answer a WHERE a.user = :user ORDER BY a.createdAt DESC")
    Optional<Answer> findLatestAnswerByUser(@Param("user") final User user);

    @Query("SELECT a FROM Answer a WHERE a.user = :user AND YEAR(a.createdAt) = :year AND MONTH(a.createdAt) = :month ORDER BY a.createdAt ASC")
    List<Answer> findByUserAndYearAndMonth(@Param("user") final User user,
                                           @Param("year") final int year,
                                           @Param("month") final int month);
}
