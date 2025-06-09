package config;

import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration //spring 환경설정
//controller,logic,dao패키지를 등록
@ComponentScan(basePackages = {"controller","logic","service","dao","aop"})
@EnableAspectJAutoProxy //AOP사용을 위한설
@EnableWebMvc //기본웹처리를 위한 설정
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


	@Bean //파일업로드처리 enctype="multipart/form-date요청 처리
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver mr = new CommonsMultipartResolver();
		mr.setMaxInMemorySize(1024*1024); //메모리에서 처리가능한 크기 지정
		mr.setMaxUploadSize(1024*10240); //업로드가능한 파일의 크기 지정
		return mr;
	}


	//예외처리 객체
	@Bean
	public SimpleMappingExceptionResolver exceptionHandler() {
		SimpleMappingExceptionResolver ser = new SimpleMappingExceptionResolver();
		Properties pr = new Properties();
		//shopException 예외가발생하면 exception.jsp호출
		pr.put("exception.ShopException", "exception");
		
		ser.setExceptionMappings(pr);
		return ser;
	}
	
	//기본웹파일 처리를 위한 설정
	@Override
	public void configureDefaultServletHandling
	(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource ms =  new ResourceBundleMessageSource();
		ms.setBasename("messages"); //messages.properties로설정
		ms.setDefaultEncoding("UTF-8");
		return ms;
	}
	
	


}


