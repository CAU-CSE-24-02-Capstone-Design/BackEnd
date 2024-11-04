package Team02.BackEnd.repository;

import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByOauthId(OauthId oauthId);

}
