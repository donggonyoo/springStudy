package springSecurity2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springSecurity2.dto.JoinDto;
import springSecurity2.service.LoginService;

import java.util.Collection;
import java.util.Iterator;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final LoginService loginService;

    @GetMapping
    public String root(Model model) {
        model.addAttribute("msg","홈으로이동합니다");
        return"home";
    }

    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("msg","home");
        return "home";
    }

    @GetMapping("my")
    public String my(Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        //인증정보조회 : 사용자정보,권한정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //권한목록조회
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        //권한을 순회
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();//등록된 권한 조회
        String role = auth.getAuthority();//권한이름 반환
        model.addAttribute("msg","/my로 접근 : id="+id+" \n\n role : "+role);
        return "home";
    }

    @GetMapping("admin")
    public String admin(Model model) {
        model.addAttribute("msg","admin");
        return "home";
    }
    // ' / , home , my , admin'  모두 반환하는 view가 같음
    // 하지만 security에 의해 my와 admin은 권한(역할)제한이 걸림
    @GetMapping("login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("join")
    public String join(Model model) {
        return "join";
    }

    @PostMapping("joinProc")
    public String joinProc(JoinDto joinDto) {
        System.out.println("PostJoin 호출");
        System.out.println(joinDto.getUsername());
        loginService.joinProcess(joinDto);
        return "redirect:/login";
    }
}
