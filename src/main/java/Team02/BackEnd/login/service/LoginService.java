package Team02.BackEnd.login.service;

import Team02.BackEnd.domain.CommonUser;
import Team02.BackEnd.repository.CommonUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final CommonUserRepository commonUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CommonUser commonUser = commonUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(commonUser.getEmail())
                .password(commonUser.getPassword())
                .roles(commonUser.getRole().name())
                .build();
    }
}
