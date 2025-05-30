package annotation;

import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
//@Component
//@EqualsAndHashCode
public class Article {
	private int id;
	private ArticleDao dao;
	public Article(int id) {
		this.id=id;
	}
}
