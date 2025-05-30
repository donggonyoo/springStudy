package kr.gd.ex02_maven;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration //spring 환경 설정해주는 기능

//kr.gd.ex02_maven 패키지 내의 클래스를 spring에 등록
@ComponentScan(basePackages = {"kr.gd.ex02_maven"})
public class AppCtx {
	
}
