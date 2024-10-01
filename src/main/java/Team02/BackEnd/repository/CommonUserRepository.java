package Team02.BackEnd.repository;

import Team02.BackEnd.domain.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonUserRepository extends JpaRepository<CommonUser, Long> {

    Optional<CommonUser> findByEmail(String email);
}
