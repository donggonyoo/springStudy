package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import exception.ShopException;
import logic.User;

/*
 *  1. poincut : UserController.idCheck* 메서드로시작
 *  		마지막 매개변수가 String,HtppSession인 메서드
 */
@Component
@Aspect
public class UserLoginAspect {
	//UserController에 idCheck로 시작하는 모든 메서드를 실행하기 전,후에 해당메서드호출
	@Around("execution(* controller.User*.idCheck*(..)) && args(..,userid,session)")
	public Object userIdCheck(ProceedingJoinPoint joinPoint,
			String userid,HttpSession session) throws Throwable{
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser==null) {
			throw new ShopException("[idCheck]로그인 필요", "login");			
		}
		if(!loginUser.getUserid().equals("admin")
				&& !loginUser.getUserid().equals(userid)) {
			throw new ShopException("[idCheck]본인 정보만 거래가능", "../item/list");
		}
		return joinPoint.proceed();
	}

}
