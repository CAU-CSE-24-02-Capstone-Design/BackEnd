package Team02.BackEnd.service;

import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    public String getJwtToken(String email) {
        return jwtService.createAccessToken(email);
    }
}
