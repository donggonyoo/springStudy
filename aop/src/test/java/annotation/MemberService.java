package annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MemberService {
	
	public void regist(Member mem) {
		System.out.println("MemberService.regist(Member) 메서드 호출");
	}
	
	public boolean update(String memberid,UpdateInfo info) {
		System.out.println(
	"MemberService.update(String,UpdateInfo)메서드호출");
		return true;
	}
	
	public boolean delete(String memberid,String name , UpdateInfo info) {
		System.out.println(
				"MemberService.delete(String,String,UpdateInfo)메서드호출");
		return false;
	}

}
