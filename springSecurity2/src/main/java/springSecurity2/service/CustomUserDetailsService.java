package springSecurity2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springSecurity2.dto.CustomUserDetails;
import springSecurity2.entity.UserEntity;
import springSecurity2.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    /*
        springSecurity에서 로그인시 호출하는 메서드
        username : 입력한 id값
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username);
        if(userData != null) {
            //Spring Security가 이를 사용해 인증(예: 비밀번호 검증)을 진행하도록 합니다.
            return new CustomUserDetails(userData);
        }
        else{
            log.error("아이디오류");
            //spring security에 인증에 실패했음을 전달
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }
}
