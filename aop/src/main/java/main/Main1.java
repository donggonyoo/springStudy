package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import annotation.Article;
import annotation.Member;
import annotation.MemberService;
import annotation.ReadArticleService;
import annotation.UpdateInfo;
import kr.gd.aop.AppCtx;

/*
 * 1.환경설정 어노테이션
 * @Configuration : 스프링 환경설정해주는 클래스
 * @ComponentScan : 객체생성을 위한 패키지설정
 * @EnableAspectJAutoProxy : AOP를 사용하도록 설정
 * @Bean : 객체를 생성해줌(Configuration내에서만 사용)
 * 
 * 2.클래스에서 사용되는 어노테이션
 * @Component : 객체화되는 클래스
 * @Autowird : 자료형기준으로 객체주입(주입대상의 객체가없다면 오류)
 * @Autowird(required=false) : 주입대상의 객체가 없으면 null로 주입
 * @Scope : 일회용객체생성 ( 사용될떄마다 새로운객체로생성됨)
 * 
 * 3.AOP관련 어노테이션
 * @Aspect : AOP로 사용될 클래스로 지정
 * @Order(순서) : 순서는 before기준 . after의 경우는 역순실행
 * @Before : 핵심기능 수행 이전
 * @AfterReturning : 핵심기능 정상적으로 실행 후 리턴값조회 
 * @AfterThrowing : 핵심기능 예외발생 시  
 * @After : 핵심기능 수행 이후
 * @Arround: 핵심기능 수행 이전 , 이후 처리 
 */
public class Main1 {
	public static void main(String[] args) {
		//ApplicationContext : 컨테이너(객체들을 저장하는공간)
		ApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppCtx.class);
		//AppCtx에 @cofiguration이 없다면 동작하지않을거임(spring 환경설정)
		
		ReadArticleService service =
				ctx.getBean("readArticleService",ReadArticleService.class);
		//타입은 ReadArticleService이긴하지만 Impl(구현체)을 호출할거임
		//(@Component("readArticleSerice") 로 설정해놨음)
		
		try {
			//a1 : Article id값이 1인객체
			Article a1 = service.getArticleAndReadCnt(1);
			Article a2 = service.getArticleAndReadCnt(1);
			System.out.println("a1.hashCode() : "+a1.hashCode());
			System.out.println("a2.hashCode() : "+a2.hashCode());
			System.out.println("[main]a1==a2 : "+(a1==a2));
			service.getArticleAndReadCnt(0);
		} 
		catch (Exception e) {
			System.out.println("[main1_Exception] : "+e.getMessage());
		}
		System.out.println("\n @--- UpdateMemberInfoTraceAspect연습 ---@");
		MemberService ms = ctx.getBean("memberService",MemberService.class);
		ms.regist(new Member());
		//regist메서드는 updateInfo를 매개변수로가지지않음
		
		ms.update("hong", new UpdateInfo());
		ms.delete("hong2", "test", new UpdateInfo());
	}
}
