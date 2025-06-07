package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import logic.User;
import service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("*")
	public ModelAndView form() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(new User());
		return mav;
	}

	@PostMapping("join")
	public ModelAndView joinForm(@Valid User user , BindingResult bresult) {
		ModelAndView mav = new ModelAndView();
		if(bresult.hasErrors()) {
			//추가 오류메시지 등록(globalErrors)
			bresult.reject("error.input.user");
			bresult.reject("error.input.check");
			return mav;
		}
		//정상입력
		try {
			service.userInsert(user);

		} catch (DataIntegrityViolationException e) {
			bresult.reject("error.duplicate.user");			
			e.printStackTrace();
			return mav;
		}
		catch(Exception e) {
			System.err.println(e.getClass());
			return mav;
		}
		mav.setViewName("redirect:login");
		return mav;
	}

	/*
	 *userid맞는 User를 DB에서조회
	 *비밀번호검증
	 *일치 : session.setAttribute("loginUser",dbUser) 
	 *mypage로 페이지이동
	 *
	 *불일치 : 비밀번호를 확인하세요 . 출력 error.login.password
	 *
	 * 
	 */
	/*@PostMapping("login")
	public  ModelAndView loginForm(User user,BindingResult bresult,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if(bresult.hasErrors()) {
			bresult.reject("error.input.check");
			return mav;
		}
		if(service.selectUser(user,session)) {
			mav.setViewName("redirect:mypage");
			return mav;
		}
		else {
			bresult.reject("error.login.password");
			return mav;
		}
	}*/
	@PostMapping("login")
	public ModelAndView login(User user,BindingResult bresult, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if(user.getUserid().trim().length()<3 
				|| user.getUserid().trim().length()>10) {
			//@Valid 어노테이션을 사용하지않고  등록방식으로처리
			//messages.properties 파일에 : error.required.userid로 등록
			bresult.rejectValue("userid", "error.required");
		}
		if(user.getPassword().trim().length()<3 
				|| user.getPassword().trim().length()>10) {
			bresult.rejectValue("password", "error.required");
		}
		User dbUser = service.selectUser(user.getUserid());
		if(dbUser == null) { //아이디존재X
			bresult.reject("error.login.id");
			return mav;
		}
		if(user.getPassword().equals(dbUser.getPassword())) { //비밀번호일치
			session.setAttribute("loginUser", dbUser);
			//mypage에서 파라미터로넘긴 아이디를 이용해 유저검증 실시
			mav.setViewName("redirect:mypage?userid=" +user.getUserid());
		}else {
			bresult.reject("error.login.password");
			return mav;
		}
		return mav;
	}
	
	@RequestMapping("mypage")
	public ModelAndView idCheckMypage(String userid, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		//아이디를이용해 객체를 뽑음
		User user = service.selectUser(userid);
		mav.addObject("user",user);
		return mav;
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
	
	//해당 메서드도AOP방식으로로그인검증을 함 !!!
	//(하지만 하나의Mapping으로 두가지를 처리하는 방식은 비추임)
	@GetMapping({"update","delete"})
	public ModelAndView idCheckUser(String userid,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = service.selectUser(userid);
		mav.addObject(user);
		return mav;
	}
	


}
