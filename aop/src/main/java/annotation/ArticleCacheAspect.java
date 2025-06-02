package annotation;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect 
@Order(2) //우선순위 2위 
public class ArticleCacheAspect {

    private final LoggingAspect loggingAspect;

	 private Map<Integer, Article> cache = new HashMap<>();

    ArticleCacheAspect(LoggingAspect loggingAspect) {
        this.loggingAspect = loggingAspect;
    }
	 
    //ReadArticleService의 모든메서드
	 @Around("execution(public * *..ReadArticleService.*(..))")
	 public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
		 Integer id = (Integer)joinPoint.getArgs()[0];
		 System.out.println("[ACA]"
				 +joinPoint.getSignature().getName()+"("+id+") 메서드호출 전");
		 
		 Article article = cache.get(id);
		 if(article!=null) {
			 System.out.println("[ACA] cache에서 Article["+id+"] 가져옴");
			 return article;
		 }
		 //다음 메서드 호출
		 Object ret = joinPoint.proceed();
		 
		 System.out.println("[ACA]"+joinPoint.getSignature().getName()
				 +"("+id+") 메서드 호출 후");
		 //ret이 null이 아니면서 Article로 형변환이 가능할 때 
		 if(ret != null && ret instanceof Article) {
			 cache.put(id, (Article)ret);
			 System.out.println("[ACA] cache에 Article["+id+"] 추가함");
		 }
		 return ret;
	 }
}
