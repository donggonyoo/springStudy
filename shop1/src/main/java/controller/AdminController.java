package controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dao.mapper.UserMapper;
import logic.User;
import service.UserService;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	UserService service;
	
	@GetMapping("/list")
	public String callList(Model model,HttpSession session) {
		List<User> list = service.selectList(); //모든 User객체를 꺼내옴
		model.addAttribute("list",list);
		return "/admin/list";
	}
}
