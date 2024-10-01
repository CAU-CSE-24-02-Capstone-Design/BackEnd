package Team02.BackEnd.repository;

import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.JavaBean;
import java.util.Optional;

public interface OauthUserRepository extends JpaRepository<OauthUser, Long> {

    Optional<OauthUser> findByOauthId(OauthId oauthId);
}
