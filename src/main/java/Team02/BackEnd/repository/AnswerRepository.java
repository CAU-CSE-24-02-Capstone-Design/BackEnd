package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Answer;
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

    @Query("SELECT a.id FROM Answer a WHERE a.user.id = :userId ORDER BY a.createdAt DESC")
    List<Long> findLatestAnswerIdByUserIdWithSize(@Param("userId") final Long userId, final Pageable pageable);

    @Query("SELECT new Team02.BackEnd.dto.answerDto.AnswerDto$AnswerIdDto(a.id, a.createdAt) FROM Answer a WHERE a.user.id = :userId")
    List<AnswerDto.AnswerIdDto> findAnswerIdDtosByUserId(@Param("userId") final Long userId);

    @Query("SELECT new Team02.BackEnd.dto.answerDto.AnswerDto$AnswerIdDto(a.id, a.createdAt) FROM Answer a WHERE a.user.id = :userId ORDER BY a.createdAt DESC")
    List<AnswerDto.AnswerIdDto> findLatestAnswerIdDtosByUserIdWithSize(@Param("userId") final Long userId,
                                                                       final Pageable pageable);

    @Query("SELECT new Team02.BackEnd.dto.answerDto.AnswerDto$AnswerIdDto(a.id, a.createdAt) FROM Answer a WHERE a.user.id = :userId AND YEAR(a.createdAt) = :year AND MONTH(a.createdAt) = :month ORDER BY a.createdAt ASC")
    List<AnswerDto.AnswerIdDto> findAnswerIdDtosByUserAndYearAndMonth(@Param("userId") final Long userId,
                                                                      @Param("year") final int year,
                                                                      @Param("month") final int month);

    @Query("SELECT new Team02.BackEnd.dto.answerDto.AnswerDto$AnswerIdDto(a.id, a.createdAt) FROM Answer a JOIN a.question WHERE a.user.id = :userId AND a.question.level = :level")
    List<AnswerDto.AnswerIdDto> findAnswerIdDtosWithLevelByUserId(@Param("userId") final Long userId,
                                                                  @Param("level") final Long level);

    @Query("SELECT new Team02.BackEnd.dto.answerDto.AnswerDto$AnswerQuestionDto(a.id, a.question.description) FROM Answer a JOIN a.question where a.user.id = :userId ORDER BY a.createdAt DESC")
    List<AnswerDto.AnswerQuestionDto> findLatestAnswerQuestionDtosByUserIdWithSize(@Param("userId") final Long userId,
                                                                                   final Pageable pageable);
}
