package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findByUserId(Long userId);
}
