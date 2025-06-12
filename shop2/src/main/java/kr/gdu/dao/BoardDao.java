package kr.gdu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.gdu.dao.mapper.BoardMapper;
import kr.gdu.dao.mapper.ItemMapper;
import kr.gdu.logic.Board;

@Repository
public class BoardDao {
	@Autowired //SqlSessionTemplate주입 (DBConfig에서 @bean으로 등록 돼 있음)
	private SqlSessionTemplate template;
	
	private Map<String,Object> param = new HashMap<>();
	private final Class<BoardMapper> cls = BoardMapper.class;
	
	public int count(String boardid, String searchtype, String searchcontent) {
		param.clear();
		param.put("boardid", boardid);
		param.put("searchtype", searchtype);
		param.put("searchcontent", searchcontent);
		return template.getMapper(cls).count(param);
	}

	public List<Board> list(Integer pageNum, int limit, String boardid, String searchtype, String searchcontent) {
		param.clear();
		param.put("pageNum", pageNum);
		param.put("limit", limit);
		param.put("boardid", boardid);
		param.put("searchtype", searchtype);
		param.put("searchcontent", searchcontent);
		return template.getMapper(cls).list(param);
	}
	
	
}
