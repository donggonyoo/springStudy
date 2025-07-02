package dong.demo.controller;

import dong.demo.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    private final List<User> users = new ArrayList<User>();

    @GetMapping("users")
    public String getUsers() {
        System.out.println("접근완료");
        return "user/users";
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


}
