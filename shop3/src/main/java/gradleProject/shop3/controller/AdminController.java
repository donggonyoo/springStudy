package gradleProject.shop3.controller;

import gradleProject.shop3.domain.Mail;
import gradleProject.shop3.domain.User;
import gradleProject.shop3.dto.MailDto;
import gradleProject.shop3.exception.ShopException;
import gradleProject.shop3.service.UserService;
import gradleProject.shop3.util.CipherUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	UserService service;



	
	@GetMapping("/list")
	public String callList(Model model,HttpSession session) throws Exception {
		List<User> list = service.selectList(); //모든 User객체를 꺼내옴
		for (User u : list) {
			if(u.getEmail()!=null && u.getEmail().length()>0) {
				u = CipherUtil.emailDecrypt(u);
			}
		}
		System.out.println("user::::"+list);
		model.addAttribute("list",list);
		return "admin/list";
	}
	
	@GetMapping("mail")
	public String mail(@RequestParam String[] idchks , HttpSession session , Model model ) throws Exception {
		System.out.println(Arrays.toString(idchks));
		System.out.println("getMailForm");
		if(idchks==null || idchks.length==0) {
			throw new ShopException("메일을 보낼 대상자를 선택하세요","list");
		}
		//idchks파라미터에 속한 userid에 해당하는 User객체들
		List<User> list = service.getUserList(idchks);
		System.out.println("@@list@@ ::: "+list);
		model.addAttribute("list",list);
		MailDto mailDto = new MailDto();
		StringBuilder sb = new StringBuilder();
		for(User u : list) {
			u = CipherUtil.emailDecrypt(u);
			sb.append(u.getUsername()).append("<").append(u.getEmail())
			.append(">").append(",");
		}
		System.out.println(sb.toString());
		mailDto.setRecipient(sb.toString());
		model.addAttribute("mailDto",mailDto);
		return "admin/mail";
	}
//
	@PostMapping("mail")
	public String mail(@Valid MailDto mail, BindingResult bresult,
					   Model model) {
		System.out.println("mailPostMapping실행");
		if(bresult.hasErrors()) {
			return "admin/mail";
		}
		System.out.println("mail :: "+mail);
		if(service.mailSend(mail)) {
			model.addAttribute("message","메일전송이완료되었습니다");
		}
		else {
			model.addAttribute("message","메일전송을 실패했습니다");
		}
		model.addAttribute("url","list");
		//첨부파일제거하기
		//service.mailfileDelete(mail);
		return "alert";
	}
}
