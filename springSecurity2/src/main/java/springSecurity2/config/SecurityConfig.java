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

        http.exceptionHandling(exception -> {
            exception.accessDeniedPage("/accessDenied"); //예외발생시(권한부족) 이동할 페이지
        });

        http.authorizeHttpRequests((auth)->auth
                .requestMatchers("/","/login","/join","/joinProc").permitAll() //home은 누구나 접근가능하다

                .requestMatchers("/admin/*").hasRole("ADMIN")
                //UserEntity에서 role 필드가 "ADMIN"으로 설정되어 있어야 하며, UserDetails 객체에 이 역할이 반영되어야 합니다.
                .requestMatchers("/pro/*").hasAnyRole("PRO","ADMIN")
                //'ADMIN' or 'USER' or 'PRO' 권한을 가져야함(즉 로그인된상태여야한단소리임)
                .requestMatchers("/my*","/home").hasAnyRole("ADMIN","USER","PRO")
                //이 외의 모든요청은 (권한이 있어야 접근가능함 권한이름은 지정X)
                .anyRequest().authenticated());

        //커스텀로그인
        //loginPage("/login")로 인해 인증되지 않은 사용자가 보호된 URL에 접근하면 /login으로 리다이렉트됩니다.
        http.formLogin((a)->a.loginPage("/login") //로그인요청
        .loginProcessingUrl("/loginProc")
                /* /loginProc으로 요청이들어오면 security가 이를 가로채 로그인처리 시작
                    이 과정에서 UserDetailsService의 구현체가 자동으로 호출 됨
                 */

         .defaultSuccessUrl("/my",true) //로그인성공 시 호출
                /*
                (로그인성공의 기준 : 권한 과 userDetail구현체 두개 다 검사 현재 로그인은 권한은 상관없으니 userDetail구현체 존재의 유무만 확인)
                true : 무조건my페이지요청
                false : 로그인 전에 요청하던 페이지가있는 경우 해당페이지로감 그 외는 /my
                 */
                .permitAll()
        );

        http.logout((a)->a.logoutUrl("/logout")//logout이라는 요청이들어오면?
                .logoutSuccessUrl("/login") //성공시 login 페이지로
                .invalidateHttpSession(true)//세션초기화
                //쿠키 삭제 (세션을 등록하면 cookie에 JSESSIONID라는 이름으로 만들어짐)
                .deleteCookies("JSESSIONID")
                .permitAll());//누구나 접근가능

        http.sessionManagement((auth)->{
            auth.sessionFixation()//사용자가 로그인할 때 기존 세션 ID를 유지하지 않고 새로운 세션 ID를 발급하도록 설정합니다.
                    .changeSessionId()
                    .maximumSessions(1)//id별 session 수를 1개로 제한(중복 로그인 방지)
                    .maxSessionsPreventsLogin(true);
        });
        /*CSRF공격을 해제하라는 의미 , springSecurity는 기본적으로 POST,PUT,DELETE 요청시에는 CSRF토큰 요구*/
        //세션기반 인증시에는 활성화하는 것이 안전함
      http.csrf((auth)->auth.disable());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
