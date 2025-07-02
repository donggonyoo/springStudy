package dong.demo.controller;


import dong.demo.domain.User;
import dong.demo.dto.UserDto;
import dong.demo.service.UserSerivce;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final List<User> users = new ArrayList<User>();
    private final UserSerivce service;

    @GetMapping("users")
    public String getUsers(Model model , HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if(userId==null){
            List<UserDto> all = service.findAll();
            System.out.println(all);
            System.out.println("접근완료");
            model.addAttribute("users", all);
            return "user/users";
        }
        else{
            model.addAttribute("users", userId);
            return "user/update";
        }

    }
    @PostMapping("users")
    public String addUser(@RequestParam String postUserId) {
        System.out.println("POST : "+postUserId);
        return "user/users";
    }
    @DeleteMapping("users")
    public String deleteUser(@RequestParam String deleteUserId) {
        System.out.println("delete : "+deleteUserId);
        return "user/users";
    }
    @PutMapping("users")
    public String updateUser(@RequestParam String updateUserId) {
        System.out.println("PUT : "+updateUserId);
        return "user/users";
    }


}
