package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.answerDto.AnswerDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUserId(final Long userId);

    @Query("SELECT a.id FROM Answer a WHERE a.user.id = :userId")
    List<Long> findAnswerIdsByUserId(@Param("userId") final Long userId);

    @Query("SELECT new Team02.BackEnd.dto.answerDto.AnswerDto$AnswerIdDto(a.id, a.createdAt) FROM Answer a WHERE a.user.id = :userId")
    List<AnswerDto.AnswerIdDto> findAnswerIdByUserId(@Param("userId") final Long userId);

    @Query("SELECT new Team02.BackEnd.dto.answerDto.AnswerDto$AnswerIdDto(a.id, a.createdAt) FROM Answer a WHERE a.user.id = :userId ORDER BY a.createdAt DESC")
    List<AnswerDto.AnswerIdDto> findLatestAnswerIdByUserIdWithSize(@Param("userId") final Long userId,
                                                                   final Pageable pageable);

    @Query("SELECT new Team02.BackEnd.dto.answerDto.AnswerDto$AnswerIdDto(a.id, a.createdAt) FROM Answer a WHERE a.user.id = :userId AND YEAR(a.createdAt) = :year AND MONTH(a.createdAt) = :month ORDER BY a.createdAt ASC")
    List<AnswerDto.AnswerIdDto> findByUserAndYearAndMonth(@Param("userId") final Long userId,
                                                          @Param("year") final int year,
                                                          @Param("month") final int month);

    @Query("SELECT new Team02.BackEnd.dto.answerDto.AnswerDto$AnswerLevelDto(a.id, a.createdAt, a.question.level) FROM Answer a JOIN a.question WHERE a.user.id = :userId")
    List<AnswerDto.AnswerLevelDto> findAnswersWithLevelByUserId(@Param("userId") final Long userId);

    @Query("SELECT new Team02.BackEnd.dto.answerDto.AnswerDto$AnswerQuestionDto(a.id, a.question.description) FROM Answer a JOIN a.question where a.user.id = :userId ORDER BY a.createdAt DESC")
    List<AnswerDto.AnswerQuestionDto> findLatestAnswersWithQuestionByUserId(@Param("userId") final Long userId,
                                                                      final Pageable pageable);

    @Query("SELECT a.id FROM Answer a WHERE a.user.id = :userId ORDER BY a.createdAt DESC")
    List<Long> findLatestAnswerIdByUserId(@Param("userId") final Long userId, final Pageable pageable);

    @Query("SELECT a FROM Answer a WHERE a.user.id = :userId ORDER BY a.createdAt DESC")
    List<Answer> findLatestAnswerByUserId(@Param("userId") final Long userId, final Pageable pageable);


    @Query("SELECT q.description FROM Answer a JOIN a.question q WHERE a.user = :user ORDER BY a.createdAt ASC")
    List<String> findQuestionDescriptionsByUser(@Param("user") final User user, final Pageable pageable);
}
