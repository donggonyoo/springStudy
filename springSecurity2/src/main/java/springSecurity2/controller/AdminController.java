package springSecurity2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @RequestMapping("home")
    public String home(Model model) {
        model.addAttribute("msg","admin페이지");
        return "home";
    }
}
