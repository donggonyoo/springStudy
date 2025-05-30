package ex01_lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/*@Getter
@Setter
@ToString
@EqualsAndHashCode
*/
@NoArgsConstructor
@AllArgsConstructor
@Data //생성자를 제외한 어노테이션을 한번에처리
public class Hiru {
	String id;
	String pass;
	
	public static void main(String[] args) {
		Hiru h = new Hiru("zz","1234");
		Hiru h2 = new Hiru();
		h2.setId("ㅋㅋ");
		h2.setPass("9977");
		System.out.println(h);
		System.out.println(h2);
	}

}
