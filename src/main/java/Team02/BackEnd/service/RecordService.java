package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AccessTokenHandler;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.domain.oauth.User;
import Team02.BackEnd.dto.RecordRequestDto;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecordService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void getVoiceUrl(String accessToken, RecordRequestDto.GetVoiceUrlDto getVoiceUrlDto) {
        String email = jwtService.extractEmail(accessToken).orElse(null);
        if (email == null)
            throw new AccessTokenHandler(ErrorStatus._ACCESSTOKEN_NOT_VALID);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null)
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);

        System.out.println(getVoiceUrlDto.getVoiceUrl());
        user.updateVoiceUrl(getVoiceUrlDto.getVoiceUrl());
        userRepository.save(user);
    }
}
