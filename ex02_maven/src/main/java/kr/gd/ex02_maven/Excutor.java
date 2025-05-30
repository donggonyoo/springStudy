package kr.gd.ex02_maven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * @Component 
 * 해당 클래스 파일의 객체를 생성
 * ex) Excutor의 객체 :
 * 	 "excutor"이름으로 ApplcationContxt객체에저장
 */
@Component 
public class Excutor {
	
	//컨테이너에서 Worker타입의 객체인 worker를 주입해줌
	//autowired를 사용하지않는다면 초기화되지않은 객체이므로 nullpointException이 날거임
	@Autowired
	private Worker worker;
	
	public void addUnit(WorkUnit unit) {
		worker.work(unit);
	}

}
