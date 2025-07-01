package springSecurity2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;


@Configuration
@EnableWebSecurity //Spring Security를 활성화
public class SecurityConfig {
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        //HttpSessionEventPublisher 빈을 등록하여
        // 세션 생성/소멸 이벤트를 Spring Security가 감지
        return new HttpSessionEventPublisher();
    }

    /*
    HttpSecurity : spring Security 보안설정 담당객체 (HTTP요청시 인증,권한을 정의가능)
    authorizeHttpRequests : 요청 URL에 따라 권한설정
    requestMatchers : 요청url
    permitAll() : 모두허용
    anyRequest() : 그 외의 모든요청
    authenticated : 인증받아야함
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth)->auth
                .requestMatchers("/","/login","/home","/join","/joinProc").permitAll() //home은 누구나 접근가능하다
                .requestMatchers("/admin").hasRole("ADMIN")
                //UserEntity에서 role 필드가 "ADMIN"으로 설정되어 있어야 하며, UserDetails 객체에 이 역할이 반영되어야 합니다.

                .requestMatchers("/my/*").hasAnyRole("ADMIN","USER")//'ADMIN' or 'USER' 권한을 가져야함
                .anyRequest().authenticated());

        //커스텀로그인
        //loginPage("/login")로 인해 인증되지 않은 사용자가 보호된 URL에 접근하면 /login으로 리다이렉트됩니다.
        http.formLogin((a)->a.loginPage("/login") //로그인요청
        .loginProcessingUrl("/loginProc").permitAll()); //"/loginProc" : 로그인 form의 action값

        http.logout((a)->a.logoutUrl("/logout")//logout이라는 요청이들어오면?
                .logoutSuccessUrl("/login") //성공시 login 페이지로
                .invalidateHttpSession(true)//세션초기화
                //쿠키 삭제 (세션을 등록하면 cookie에 JSESSIONID라는 이름으로 만들어짐)
                .deleteCookies("JSESSIONID")
                .permitAll());//누구나 접근가능

        /*CSRF공격을 해제하라는 의미 , springSecurity는 기본적으로 POST,PUT,DELETE 요청시에는 CSRF토큰 요구*/
        //세션기반 인증시에는 활성화하는 것이 안전함
       /* http.csrf((a)->a.disable());*/
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
