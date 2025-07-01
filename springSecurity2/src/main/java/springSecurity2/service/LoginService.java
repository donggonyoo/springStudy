package springSecurity2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springSecurity2.dto.JoinDto;
import springSecurity2.entity.UserEntity;
import springSecurity2.mapper.JoinMapper;
import springSecurity2.repository.UserRepository;

@Service
@RequiredArgsConstructor //final이붙은 필드로 생성자를 만듬 (AutoWired없이 스프링에등록)
public class LoginService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public void joinProcess(JoinDto joinDto) {
        UserEntity data = new UserEntity();
        data.setUsername(joinDto.getUsername());
        //비밀번호해쉬처리(복호화 X)
        data.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        if(joinDto.getUsername().equals("admin")) {
            data.setRole("ROLE_ADMIN");
        }
        else{
            data.setRole("ROLE_USER");
        }
        userRepository.save(data);

    }
}
