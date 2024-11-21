package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.RecordRequestDto.GetVoiceUrlDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.AnswerRepository;
import Team02.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;

    public void signOut(final String accessToken) {
        //todo 회원 탈퇴시 이 회원과 관련된 모든 DB 데이터 삭제
    }

    public void updateRoleAndVoiceUrl(final String accessToken, final GetVoiceUrlDto getVoiceUrlDto) {
        User user = getUserByToken(accessToken);

        user.updateRole();
        user.updateVoiceUrl(getVoiceUrlDto.getVoiceUrl());

        userRepository.save(user);
    }

    public User getUserByToken(final String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);

        User user = userRepository.findByEmail(email).orElse(null);
        validateUserIsNotNull(user);

        return user;
    }

    public Long[] getDatesWhenUserDid(final String accessToken, final String year, final String month) {
        User user = getUserByToken(accessToken);

        Long[] answerIdDidThisPeriod = new Long[32];
        Arrays.fill(answerIdDidThisPeriod, 0L);

        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        List<Answer> answersInPeriod = answerRepository.findByUserAndYearAndMonth(user, yearInt, monthInt);

        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");

        answersInPeriod.forEach(answer -> {
            // UTC에서 서울 시간대로 변환 후, 해당 날짜의 dayOfMonth 가져오기
            int day = answer.getCreatedAt()
                    .atZone(ZoneId.of("UTC"))  // UTC에서
                    .withZoneSameInstant(seoulZoneId)  // 서울 시간대로 변환
                    .getDayOfMonth();  // 해당 날짜의 일을 가져옴
            answerIdDidThisPeriod[day - 1] = answer.getId();
        });

        return answerIdDidThisPeriod;
    }

    private void validateUserIsNotNull(final User user) {
        if (user == null) {
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);
        }
    }
}
