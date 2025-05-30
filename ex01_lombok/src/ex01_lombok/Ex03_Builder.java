package ex01_lombok;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@AllArgsConstructor
public class Ex03_Builder {
	private String id;
	private String pw;
	private String email;
	
	public static void main(String[] args) {
		Ex03_Builder e1 = Ex03_Builder.builder()
							.id("동곤유").pw("7788").email("yy@nn.com").build();
		
		System.out.println(e1);
		System.out.println("----------");
		Ex03_Builder e2 = new Ex03_Builder("동곤유2", "1234", "gg@mm.com");
		System.out.println(e2.getEmail());
		System.out.println(e2.getId());
		System.out.println(e2.getPw());
	}
}
