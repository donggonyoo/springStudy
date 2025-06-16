package kr.gdu.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.gdu.dao.BoardDao;
import kr.gdu.dao.CommDao;
import kr.gdu.dto.board.BoardCountDto;
import kr.gdu.dto.board.BoardDetailDto;
import kr.gdu.dto.board.DeleteBoardDto;
import kr.gdu.logic.Board;
import kr.gdu.logic.Comment;

@Service
public class BoardService {
	
	//appliaction.properties의 환경변수값 읽어오기
	@Value("${summernote.imgupload}")
	private String UPLOAD_IMAGE_DIR;
	
	@Autowired
	BoardDao boardDao;
	
	@Autowired
	CommDao commDao;
	
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
	public void boardDelete(DeleteBoardDto dto) {
		boardDao.delete(dto);
		
	}
	public void boardReply( Board board) {
		boardDao.grpStepAdd(board); //이미등록된 grpstep값 1씩 증가
		int max = boardDao.maxNum();
		board.setNum(++max);
		board.setGrplevel(board.getGrplevel()+1);
		board.setGrpstep(board.getGrpstep()+1);
		boardDao.insert(board);		
	}
	public List<Comment> commentList(int num) {
		return commDao.commentList(num);
		
	}
	public int comMaxSeq(int num) {		
		return commDao.comMaxSeq(num);
	}
	public void comInsert( Comment comm) {
		commDao.comInsert(comm);
		
	}
	public Comment commSelectOne(int num, int seq) {
		return commDao.commSelectOne(num,seq);
	}
	public void commDel(int num, int seq) {
		commDao.commDel(num,seq);
		
	}
	public String summernoteImageUpload(MultipartFile multipartFile) {
		File dir = new File(UPLOAD_IMAGE_DIR+"board/image");
		if(!dir.exists()) {
			dir.mkdirs();
		}
		//파일의 이름
		String fileSystemName = multipartFile.getOriginalFilename();		
		File file = new File(dir,fileSystemName);
		try {
			multipartFile.transferTo(file);	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "/board/image/"+fileSystemName;
	}

}