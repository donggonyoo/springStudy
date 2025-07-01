package springSecurity2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import springSecurity2.dto.CustomUserDetails;
import springSecurity2.entity.UserEntity;

import java.util.Collection;

@SpringBootTest
class SpringSecurity2ApplicationTests {

    @Test
    void contextLoads() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setPassword("123456");

        userEntity.setRole("admin");
        CustomUserDetails cm = new CustomUserDetails(userEntity);
        Collection<? extends GrantedAuthority> role = cm.getAuthorities();
       for (GrantedAuthority grantedAuthority : role) {
           System.out.println(grantedAuthority.getAuthority());
       }
    }

}
