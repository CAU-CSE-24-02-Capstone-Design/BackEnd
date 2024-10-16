package Team02.BackEnd.service;

import Team02.BackEnd.dto.StatisticsRequestDto.GetStatisticsDto;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    public void saveStatistics(GetStatisticsDto request, String token) {
        /**
         * 토큰 까서 유저 이메일 반환 후 유저레포에서 Long id 찾고 저장
         */
    }

    public HashMap<String, String> getFilterStatistics(String filter, String token) {

        /**
         * 토큰에서 현재 로그인 한 유저 정보 확인하고, 유저레포에서 해당 유저 id 가져옴
         * statistics 테이블에서 해당 유저 id 가진 정보 중에 filter 에 맞는 값만 정렬해서 다 가져옴
         * map으로 created at이랑 해당 값 toString 화 해서 넘겨줌
         */

        return null;
    }
}
