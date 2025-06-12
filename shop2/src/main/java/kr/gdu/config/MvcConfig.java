package kr.gdu.config;

import java.util.Properties;

import javax.xml.crypto.Data;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.zaxxer.hikari.HikariDataSource;


@Configuration //spring 환경설정
@EnableAspectJAutoProxy //AOP사용을 위한설
public class MvcConfig  implements WebMvcConfigurer{

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
	/*@Override
	public void configureDefaultServletHandling
	(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}*/
	
	@Bean
	@Primary //우선적용
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties dataSourceProperties(){ //Connection 객체		
		return new DataSourceProperties();
	}
	
	@Bean
	@ConfigurationProperties("spring.datasource.hikari")
	public HikariDataSource dataSource(DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder()
				.type(HikariDataSource.class).build(); //Connection Pool 객체
	}
	
}


