package kr.gd.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration //spring 환경 설정하는 클래스 의미
//annotation 패키지에 속한 클래스중 @Compoenet 가진 클래스의 객체를 생성 후 저장
@ComponentScan(basePackages = {"annotation"})

@EnableAspectJAutoProxy //AOP설정함 , AOP관련 어노테이션 기능 사용
public class AppCtx {

}
