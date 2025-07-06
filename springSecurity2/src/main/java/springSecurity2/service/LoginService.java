package springSecurity2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springSecurity2.dto.JoinDto;
import springSecurity2.entity.UserEntity;
import springSecurity2.mapper.JoinMapper;
import springSecurity2.repository.UserRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        else if(joinDto.getUsername().contains("pro")) {
            data.setRole("ROLE_PRO");
        }
        else{
            data.setRole("ROLE_USER");
        }
        userRepository.save(data);

    }

    public Map<String,String> getAuth() {
        HashMap<String, String> map = new HashMap<>();
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        //인증정보조회 : 사용자정보,권한정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //권한목록조회
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        //권한을 순회
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();//등록된 권한 조회
        String role = auth.getAuthority();//권한이름 반환

        //권한이 ROLE_xxx 로 지정을 시켜놨으므로 _ 뒤에 문자만 따서 url로 지정
        String roleName = role.substring(role.indexOf("_")+1).toLowerCase();
        String url = "../"+roleName+"/home";
        //    ../권한이름/home 으로 url설정
        if(roleName.equals("user")){
            url = "../home";
        }

        map.put("id",id);
        map.put("role",role);
        map.put("url",url);
        return map;
    }
}
