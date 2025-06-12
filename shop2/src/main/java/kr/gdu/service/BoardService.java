package kr.gdu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.gdu.dao.BoardDao;
import kr.gdu.dto.board.BoardCountDto;
import kr.gdu.logic.Board;

@Service
public class BoardService {
	@Autowired
	BoardDao boardDao;
	
	public int boardcount(String boardid, String searchtype, String searchcontent) {
		BoardCountDto dto = new BoardCountDto();
		dto.setBoardid(boardid);
		dto.setSearchcontent(searchcontent);
		dto.setSearchtype(searchtype);
		return boardDao.count(dto);
	}
	public List<Board> boardlist
	(Integer pageNum, int limit, String boardid, String searchtype, String searchcontent) {
		return boardDao.list(pageNum,limit,boardid,searchtype,searchcontent);
	}

}