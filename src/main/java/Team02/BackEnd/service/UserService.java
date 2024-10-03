package Team02.BackEnd.service;


import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AccessTokenHandler;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.domain.User;
import Team02.BackEnd.dto.user.UserRequest;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void updateFamilyInfo(String accessToken, UserRequest.UserFamilyInfoRequestDTO userFamilyInfoRequestDTO) {
        Optional<String> email = jwtService.extractEmail(accessToken);
        // accessToken이 유효하지 않으면
        if (email.isEmpty())
            throw new AccessTokenHandler(ErrorStatus._ACCESSTOKEN_NOT_VALID);

        Optional<User> user = userRepository.findByEmail(email.get());
        // 해당 email을 가진 user가 없으면
        if(user.isEmpty())
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);

        // user의 가족 정보 업데이트
        user.get().update(userFamilyInfoRequestDTO);
    }
}
