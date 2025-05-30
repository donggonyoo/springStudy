package annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("readArticleService")
//ReadArticleService의 구현체Impl
public class ReadArticleServiceImpl implements ReadArticleService{


	@Override
	public Article getArticleAndReadCnt(int id) throws Exception {
		//System.out.println("getArticleAndReadCnt("+id+")");
		if(id==0) {
			throw new Exception("0입력 불가");
		}
		return new Article(id);
	}

}
