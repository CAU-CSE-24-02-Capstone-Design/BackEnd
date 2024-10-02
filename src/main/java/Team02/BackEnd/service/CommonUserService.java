package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.converter.CommonUserConverter;
import Team02.BackEnd.domain.CommonUser;
import Team02.BackEnd.dto.user.CommonUserRequest;
import Team02.BackEnd.dto.user.CommonUserResponse;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.CommonUserRepository;
import Team02.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CommonUserService {

    private final CommonUserRepository commonUserRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public CommonUserResponse.CommonUserIdResponseDTO signUp(CommonUserRequest.CommonUserSignUpRequestDTO commonUserSignUpRequestDTO) {
        CommonUser user = commonUserRepository.findByEmail(commonUserSignUpRequestDTO.getEmail()).orElse(null);
        if (user != null)
            throw new UserHandler(ErrorStatus._USER_DUPLICATED);

        CommonUser commonUser = CommonUserConverter.toCommonUser(commonUserSignUpRequestDTO);
        commonUser.passwordEncode(passwordEncoder);
        commonUserRepository.save(commonUser);

        return CommonUserConverter.toCommonUserIdResponseDTO(commonUser);
    }

    public void signOut(String accessToken) {
        //todo 회원 탈퇴시 이 회원과 관련된 모든 DB 데이터 삭제
    }
}
