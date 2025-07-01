package springSecurity2.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springSecurity2.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    //DB 사용자 정보
    public UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
    /*
        GrantedAuthority : 권한 담당
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole(); //권한 부여
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { /*계정만료여부*/
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {/*비밀번호만료여부*/
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { /*계정잠김여부*/
        return true;
    }

    @Override
    public boolean isEnabled() { /*계정 활성화여부*/
        return true;
    }
}
