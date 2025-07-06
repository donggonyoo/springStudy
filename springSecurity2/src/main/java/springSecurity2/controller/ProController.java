package springSecurity2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pro")
public class ProController {

    @RequestMapping("home")
    public String ProCheck(Model model) {
        model.addAttribute("msg","pro의 페이지");
        return "home";
    }

}
