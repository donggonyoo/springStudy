package springSecurity2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springSecurity2.dto.JoinDto;
import springSecurity2.service.LoginService;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final LoginService loginService;

    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("msg","USER페이지");
        return "home";
    }
    @GetMapping("my")
    public String my(Model model) {
        Map<String, String> map = loginService.getAuth();//권한이름을 얻어내는 과정
        String id = map.get("id");
        String role = map.get("role");
        String url = map.get("url");
        model.addAttribute("msg","/my로 접근 : id="+id+" \n\n role : "+role);
        model.addAttribute("url",url);
        return "home";
    }

    // ' / , home , my , admin'  모두 반환하는 view가 같음
    // 하지만 security에 의해 my와 admin은 권한(역할)제한이 걸림
    @GetMapping("login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("hi")
    public String hi(Model model) {
        return "hi";
    }

    @GetMapping("join")
    public String join(Model model) {
        return "join";
    }

    @PostMapping("joinProc")
    public String joinProc(JoinDto joinDto) {
        loginService.joinProcess(joinDto);
        return "redirect:/login";

    }
    @RequestMapping("accessDenied")
    public String accessDenied(Model model) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String auth = authorities.iterator().next().getAuthority();
        if(auth.isEmpty()){
            auth ="권한X";
        }
        model.addAttribute("msg","권한이부족합니다(당신의 권한 : "+auth+")");
        return "accessDenied";
    }
}
