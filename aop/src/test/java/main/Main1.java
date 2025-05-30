package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import annotation.Article;
import annotation.ReadArticleService;
import kr.gd.aop.AppCtx;

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
	}
}
