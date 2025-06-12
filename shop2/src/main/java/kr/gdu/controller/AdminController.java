package kr.gdu.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import kr.gdu.logic.User;


@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	kr.gdu.service.UserService service;
	
	@GetMapping("/list")
	public String callList(Model model,HttpSession session) {
		List<User> list = service.selectList(); //모든 User객체를 꺼내옴
		model.addAttribute("list",list);
		return "/admin/list";
	}
}
