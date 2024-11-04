package Team02.BackEnd.service;

import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.RecordRequestDto.GetVoiceUrlDto;
import Team02.BackEnd.exception.validator.UserValidator;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void signOut(String accessToken) {
        //todo 회원 탈퇴시 이 회원과 관련된 모든 DB 데이터 삭제
    }

    public void updateRoleAndVoiceUrl(String accessToken, GetVoiceUrlDto getVoiceUrlDto) {
        User user = getUserByToken(accessToken);

        user.updateRole();
        user.updateVoiceUrl(getVoiceUrlDto.getVoiceUrl());

        userRepository.save(user);
    }

    public User getUserByToken(String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);

        User user = userRepository.findByEmail(email).orElse(null);
        UserValidator.validateUserIsNotNull(user);

        return user;
    }

    public HashMap<String, Long> getDatesWhenUserDid(String year, String month) {
        return null;
    }

}
