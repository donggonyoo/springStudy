	package config;
	
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.ComponentScan;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.web.servlet.HandlerMapping;
	import org.springframework.web.servlet.ViewResolver;
	import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
	import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
	import org.springframework.web.servlet.view.InternalResourceViewResolver;
	
	@Configuration //spring 환경설정
	//controller,logic,dao패키지를 등록
	@ComponentScan(basePackages = {"controller","logic","service","dao"})
	public class MvcConfig  implements WebMvcConfigurer{
		
		@Bean // configuration내에만 사용(객체등록)
		public HandlerMapping handlerMapping() {
			RequestMappingHandlerMapping hm = new RequestMappingHandlerMapping();
			hm.setOrder(0);
			return hm;
		}
		
		//컨트롤러가 반환한 뷰 이름(예: "home")을 
		//실제 JSP 파일 경로(예: /WEB-INF/view/home.jsp)로 변환하여 렌더링
		@Bean 
		public ViewResolver viewResolver() {
			InternalResourceViewResolver vr = new InternalResourceViewResolver();
			vr.setPrefix("/WEB-INF/view/");
			vr.setSuffix(".jsp");
			return vr;
		}
	}
