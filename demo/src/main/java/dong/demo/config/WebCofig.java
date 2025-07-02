package dong.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class WebCofig {
    
    //post요청 시 
    //<input type="hidden" name="_method" value="DELETE"> 가 무시되면 deleteMapping으로 넘어가지않음
    //hidden의 name과value를 무시하지않기 위한 메서드
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
        return filter;
    }
}
