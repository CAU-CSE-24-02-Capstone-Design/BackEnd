package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AccessTokenHandler;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void signOut(String accessToken) {
        //todo 회원 탈퇴시 이 회원과 관련된 모든 DB 데이터 삭제
    }

    public void updateRole(String accessToken) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        if (email == null)
            throw new AccessTokenHandler(ErrorStatus._ACCESSTOKEN_NOT_VALID);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null)
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);

        user.updateRole();
    }

    public HashMap<String, Long> getDatesWhenUserDid(String year, String month) {
        return null;
    }
}
