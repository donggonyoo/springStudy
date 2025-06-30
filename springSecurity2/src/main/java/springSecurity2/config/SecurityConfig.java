package springSecurity2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;


@Configuration
@EnableWebSecurity //Spring Security를 활성화
public class SecurityConfig {
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
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
                .requestMatchers("/","/login","/home").permitAll() //home은 누구나 접근가능하다
                .requestMatchers("/admin").hasRole("ADMIN")//사용자가 **"ADMIN" 역할(Role)**을 가져야함
                .requestMatchers("/my/*").hasAnyRole("ADMIN","USER")//'ADMIN' or 'USER' 권한을 가져야함
                .anyRequest().authenticated());

        //커스텀로그인
        //loginPage("/login")로 인해 인증되지 않은 사용자가 보호된 URL에 접근하면 /login으로 리다이렉트됩니다.
        http.formLogin((a)->a.loginPage("/login") //로그인요청
        .loginProcessingUrl("/loginProc").permitAll()); //로그인 form의 action값

        http.logout((a)->a.logoutUrl("/logout")
                .logoutSuccessUrl("/login") //성공시 login 페이지로
                .invalidateHttpSession(true)//세션초기화
                .deleteCookies("JSESSIONID")//JSESSIONID(session)삭제
                .permitAll());//누구나 접근가능
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
