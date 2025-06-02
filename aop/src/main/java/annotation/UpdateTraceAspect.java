package annotation;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class UpdateTraceAspect {
	//args(..,id,info) : 매개변수로만 pointcut 지정
	// ..,id,info : 
	//매개변수의 마지막목록이 (id)String , (info)UpdateInfo인 메서드 선택
	@AfterReturning(pointcut = "args(..,id,info)",
			argNames = "ret,id,info",returning = "ret")
	public void traceReturn(Object ret , String id , UpdateInfo info) {
		System.out.println("[TA] 정보수정 결과 : "+ret+", 대상ID : "+id+
				", 수정정보 : "+info);
	}
}
