package kr.gd.ex02_maven;

import org.springframework.stereotype.Component;

@Component //"worker"이름으로 ApplcationContxt 객체에저장
public class Worker {
	public void work(WorkUnit unit) {
		System.out.println(this+" :work : "+unit);
	}
}
