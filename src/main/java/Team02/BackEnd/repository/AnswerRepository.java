package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUserId(Long userId);

    @Query("SELECT a FROM Answer a WHERE a.user = :user AND YEAR(a.createdAt) = :year AND MONTH(a.createdAt) = :month ORDER BY a.createdAt ASC")
    List<Answer> findByUserAndYearAndMonth(@Param("user") User user,
                                           @Param("year") int year,
                                           @Param("month") int month);
}
