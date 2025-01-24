package Team02.BackEnd.repository;

import Team02.BackEnd.domain.Statistics;
import Team02.BackEnd.dto.statisticsDto.StatisticsDto.StatisticsDataDto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    @Query("SELECT new Team02.BackEnd.dto.statisticsDto.StatisticsDto$StatisticsDataDto(s.gantourCount, s.silentTime) FROM Statistics s WHERE s.answer.id = :answerId")
    Optional<StatisticsDataDto> findStatisticsDataDtoByAnswerId(@Param("answerId") final Long answerId);

    @Query("SELECT COUNT(s) > 0 FROM Statistics s WHERE s.answer.id = :answerId")
    boolean existsByAnswerId(@Param("answerId") Long answerId);
}
