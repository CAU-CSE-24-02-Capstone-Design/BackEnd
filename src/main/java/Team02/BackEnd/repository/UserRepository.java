package Team02.BackEnd.repository;

import Team02.BackEnd.domain.oauth.OauthId;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.userDto.UserDto.UserAnswerIndexDto;
import Team02.BackEnd.dto.userDto.UserDto.UserVoiceDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(final String email);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Optional<Long> findUserIdByEmail(@Param("email") final String email);

    @Query("SELECT new Team02.BackEnd.dto.userDto.UserDto$UserAnswerIndexDto(u.id, u.analyzeCompleteAnswerIndex) FROM User u WHERE u.email = :email")
    Optional<UserAnswerIndexDto> findUserAnswerIndexByEmail(@Param("email") final String email);

    @Query("SELECT new Team02.BackEnd.dto.userDto.UserDto$UserVoiceDto(u.id, u.name, u.voiceUrl) " +
            "FROM User u WHERE u.email = :email")
    Optional<UserVoiceDto> findUserDataByEmail(@Param("email") final String email);

    Optional<User> findByOauthId(final OauthId oauthId);
}
