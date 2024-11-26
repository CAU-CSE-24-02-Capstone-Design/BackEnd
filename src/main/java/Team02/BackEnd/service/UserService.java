package Team02.BackEnd.service;

import static Team02.BackEnd.constant.Constants.BASE_TIME_ZONE;
import static Team02.BackEnd.constant.Constants.NEW_TIME_ZONE;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.domain.Answer;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.recordDto.RecordRequestDto.GetVoiceUrlDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.AnswerRepository;
import Team02.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void signOut(final String accessToken) {
        log.info("회원 탈퇴");
        //todo 회원 탈퇴시 이 회원과 관련된 모든 DB 데이터 삭제
    }

    public void updateRoleAndVoiceUrl(final String accessToken, final GetVoiceUrlDto getVoiceUrlDto) {
        User user = getUserByToken(accessToken);
        user.updateRole();
        user.updateVoiceUrl(getVoiceUrlDto.getVoiceUrl());
        userRepository.save(user);
        log.info("사용자의 목소리 저장, userId : {}", user.getId());
    }

    public User getUserByToken(final String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        User user = userRepository.findByEmail(email).orElse(null);
        validateUserIsNotNull(user);
        return user;
    }

    private void validateUserIsNotNull(final User user) {
        if (user == null) {
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);
        }
    }
}
