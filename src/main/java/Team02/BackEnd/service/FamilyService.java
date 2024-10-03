package Team02.BackEnd.service;

import Team02.BackEnd.apiPayload.code.status.ErrorStatus;
import Team02.BackEnd.apiPayload.exception.handler.AccessTokenHandler;
import Team02.BackEnd.apiPayload.exception.handler.UserHandler;
import Team02.BackEnd.domain.Family;
import Team02.BackEnd.domain.User;
import Team02.BackEnd.jwt.service.JwtService;
import Team02.BackEnd.repository.FamilyRepository;
import Team02.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void connect(String accessToken) {
        Optional<String> email = jwtService.extractEmail(accessToken);
        // accessToken이 유효하지 않으면
        if (email.isEmpty())
            throw new AccessTokenHandler(ErrorStatus._ACCESSTOKEN_NOT_VALID);

        Optional<User> user = userRepository.findByEmail(email.get());
        // 해당 email을 가진 user가 없으면
        if (user.isEmpty())
            throw new UserHandler(ErrorStatus._USER_NOT_FOUND);

        List<User> familyMember = userRepository.findByFamilyCode(user.get().getFamilyCode());
        // 같은 가족 코드를 가진 유저가 있는데, 자기 자신이라면 == 같은 가족 코드를 가진 유저가 없다면, 가족을 구성하지 않는다.
        if (familyMember.size() == 1)
            return;

        User partner = null;
        for (User u : familyMember) {
            if (!u.getId().equals(user.get().getId())) {
                partner = u;
            }
        }

        // user : 부모, familyMember : 자식
        Family family = null;
        if (user.get().isParent()) {
            family = Family.builder()
                    .child(partner)
                    .parent(user.get())
                    .build();
        } else {
            family = Family.builder()
                    .child(user.get())
                    .parent(partner)
                    .build();
        }
        familyRepository.save(family);
    }
}
