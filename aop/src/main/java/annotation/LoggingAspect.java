package annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect //AOP 처리클래스
@Order(3) //우선순위 3번쨰
public class LoggingAspect {
	/*
	 * AOP 관련 용어
	 * 		pointcut : 핵심메서드 설정
	 * [접근제어자] * annotation : annotation패키지 하위 메서드의 모든 리턴 타입
	 * 
	 * advice : 실행되는 시점 설정
	 * 1) Before : 메서드 실행 전
	 * 2) After : 핵심메서드 실행 후
	 * 3) afterReturning : 핵심메서드 정상 실행 후
	 * 4) afterThrowing : 핵심메서드 오류 발생 후
	 * 5) around : 핵심메서드 실행 전,후
	 */
	
	//pointcut 설정 
	final String publicMethod   = "execution(public * annotation..*(..))";
	// annotation 패키지 및 하위 패키지의 모든 public 메서드에 Aspect를 적용
	
	//advice설정
	@Before(publicMethod)
	public void before() {
		System.out.println("[LA]Before 메서드 실행 전 호출");	
	}
	
	@AfterReturning(pointcut=publicMethod,returning="ret")
	public void afterReturning(Object ret) {
		System.out.println("[LA]AfterReturning 메서드 정상종료 후 호출, 리턴값 : "+ret);
	}
	
	@AfterThrowing(pointcut=publicMethod,throwing ="e")
	public void afterThrowing(Throwable e) {
		System.out.println("[LA]afterThrowing 메서드 정상종료 후 호출, 예외msg : "+e.getMessage());
	}
	
	@After(value=publicMethod)
	public void afterFinally() {
		System.out.println("[LA] After 메서드 종료 후 실행");
	}
	

}
