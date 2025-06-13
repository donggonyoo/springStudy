package kr.gdu.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.gdu.dao.BoardDao;
import kr.gdu.dto.board.BoardCountDto;
import kr.gdu.dto.board.BoardDetailDto;
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
	public Board getBoard(int num) {
		return boardDao.selectOne(num);
		
	}
	public void addReadcnt(int num) {
		boardDao.addReadcnt(num);
		
	}
	public void boardWrite(Board board, HttpServletRequest request) {
		int maxNum = boardDao.maxNum();
		board.setNum(++maxNum);
		board.setGrp(maxNum);
		if(board.getFile1() != null && !board.getFile1().isEmpty()) {
			String path = 
					request.getServletContext().getRealPath("/")+"board/file/";
			this.uploadFileCreate(board.getFile1(),path);
			board.setFileurl(board.getFile1().getOriginalFilename());
			
		}
		boardDao.insert(board);
		
	}
	
	private void uploadFileCreate(MultipartFile file, String path) {
		String orgFile = file.getOriginalFilename();
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		try {
			file.transferTo(new File(path+orgFile));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void boardUpdate(Board board, HttpServletRequest request) {
		if(board.getFile1()!=null && !board.getFile1().isEmpty()) {
			String path = 
					request.getServletContext().getRealPath("/")+"board/file/";
			this.uploadFileCreate(board.getFile1(),path);
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		boardDao.update(board);
		
	}

}