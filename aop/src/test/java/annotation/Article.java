package annotation;

import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
//@Component 아마 해당어노테이션을 이용해 객체를 등록해준다면 autowird를 사용할수도있으며
//@EqualsAndHashCode 객체의 주소값으로비교하지않고 값으로 동등성을 따질거임
public class Article {
	private int id;
	private ArticleDao dao;
	public Article(int id) {
		this.id=id;
	}
}
