package kr.gd.ex02_maven;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main1 {
	public static void main(String[] args) {
		//이 설정 클래스를 기반으로 Spring 컨테이너가 빈을 생성하고 관리합니다.
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext(AppCtx.class);
		
		//이름이 "excutor"이며 타입이 Excutor인 빈을 가져옴
		
		Excutor exec = ctx.getBean("excutor",Excutor.class);
		exec.addUnit(new WorkUnit());
		exec.addUnit(new WorkUnit());
		
	}

}
