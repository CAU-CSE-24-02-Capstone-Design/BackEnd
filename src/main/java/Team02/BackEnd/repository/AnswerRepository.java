package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUserId(Long userId);
}
