package springSecurity2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class HomeController {

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
        model.addAttribute("msg","my");
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
}
