package springSecurity2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springSecurity2.dto.CustomUserDetails;
import springSecurity2.entity.UserEntity;
import springSecurity2.repository.UserRepository;

@Service
@RequiredArgsConstructor
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
            return new CustomUserDetails(userData);
        }
        else{
            System.out.println("아이디 오류");
            throw new UsernameNotFoundException("USER NOT FOUND");
        }

    }
}
