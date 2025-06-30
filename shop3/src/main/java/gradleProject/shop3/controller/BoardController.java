package gradleProject.shop3.controller;

import gradleProject.shop3.domain.Board;
import gradleProject.shop3.domain.Comment;
import gradleProject.shop3.dto.board.BoardDto;
import gradleProject.shop3.dto.board.CommentDto;
import gradleProject.shop3.dto.board.DeleteBoardDto;
import gradleProject.shop3.exception.ShopException;
import gradleProject.shop3.mapper.CommentMapper;
import gradleProject.shop3.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;



@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {

    private final ItemController itemController;
    private final UserController userController;
	private final CommentMapper commentMapper;

	@Autowired
	private BoardService service;

//	@GetMapping("*")
//	public ModelAndView write() {
//		ModelAndView mav = new ModelAndView();
//		mav.addObject(new BoardDto());
//		return mav;
//	}
	/* Spring에서 파라미터 전달 방식
	 *   1. 파라미터이름과 매개변수의 이름이 같은 경우 매핑
	 *   2. Bean 클래스의 프로퍼티명과 파라미터이름이 같은 경우 매핑
	 *   3. Map 객체에 RequestParam 어노테이션을 이용한 매핑
	 * 
	 * @RequestParam : 파라미터값을 Map 객체에 매핑하여 전달
	 */
	@GetMapping("list")
	public String list(@RequestParam(value="boardid", defaultValue = "1") String boardid,
					   @RequestParam(value="pageNum", defaultValue = "1") Integer pageNum,
					   @RequestParam(value="searchtype", required = false) String searchtype,
					   @RequestParam(value="searchcontent", required = false) String searchcontent,
					   Model model) {

		int limit = 10; // 페이지당 게시물 수
		Page<Board> paging = service.boardlist(pageNum, limit, boardid, searchtype, searchcontent);


		int listcount = (int) paging.getTotalElements();
		int maxpage = paging.getTotalPages();
		int startpage = (pageNum - 1) / limit * limit + 1;
		int endpage = startpage + limit - 1;
		if(endpage > maxpage) endpage = maxpage;
		int boardno = listcount - (pageNum - 1) * limit;

		model.addAttribute("paging", paging);
		model.addAttribute("boardlist", paging.getContent());
		model.addAttribute("boardid", boardid);
		model.addAttribute("boardName", getBoardName(boardid));
		model.addAttribute("listcount", listcount);
		model.addAttribute("boardno", boardno);
		model.addAttribute("startpage", startpage);
		model.addAttribute("endpage", endpage);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("maxpage", maxpage);
		model.addAttribute("searchtype", searchtype);
		model.addAttribute("searchcontent", searchcontent);
		return "board/list";
	}

	private String getBoardName(String boardid) {
		if (!StringUtils.hasText(boardid)) return "게시판";
		return switch (boardid) {
			case "1" -> "공지사항";
			case "2" -> "자유게시판";
			case "3" -> "Q&A";
			default -> "게시판";
		};
	}

	@GetMapping("detail")
	public String detail(@RequestParam int num,Model model ) {
		Board board = service.getBoard(num);
		service.addReadcnt(num);
		String boardName = null;
		String boardid = board.getBoardid();
		if(boardid==null) {
			boardid="1";
		}
		switch(board.getBoardid()) {
		   case "1" -> boardName = "공지사항";
		   case "2" -> boardName = "자유게시판";
		   case "3" -> boardName = "QNA";
		}
		//댓글목록 화면에전달
		List<Comment> commlist = service.commentList(num);
		Comment comm = new Comment();
		BoardDto boardDto = new BoardDto(board);
		System.out.println("boardDTo : "+boardDto);
		comm.setNum(num);
		CommentDto dto = commentMapper.toDto(comm);
		model.addAttribute("board",boardDto);
		model.addAttribute("boardName",boardName);
		model.addAttribute("commlist",commlist);
		model.addAttribute("commentDto",dto);
		return "board/detail";
	}

	@GetMapping("write")
	public ModelAndView write() {
			ModelAndView mav = new ModelAndView();
			mav.addObject(new BoardDto());
			return mav;
		}

	@PostMapping("write")
	public String writePost(@Valid BoardDto dto , BindingResult bresult,
			HttpServletRequest request) {
		if(bresult.hasErrors()) {
			// /WEB-INF/views/board/write.jsp 페이지로
			return "board/write";
		}
		if(!StringUtils.hasText(dto.getBoardid())) {
			dto.setBoardid("1");
		}
		service.boardWrite(dto,request);
		return "redirect:list?boardid="+dto.getBoardid();
	}
//
@GetMapping("update")
public String updateForm(@RequestParam int num,@RequestParam String boardid, Model model) {
	Board board = service.getBoard(num);
	if (board == null) throw new ShopException("수정할 게시물이 없습니다.", "list?boardid=1");
	BoardDto boardDto = new BoardDto(board);
	model.addAttribute("boardDto", boardDto);
	model.addAttribute("boardName", getBoardName(board.getBoardid()));
	model.addAttribute("num", num);
	model.addAttribute("boardid", boardid);
	return "board/update";
}

	@GetMapping("delete")
	public String deleteForm(@RequestParam int num,@RequestParam String boardid, Model model) {
		Board board = service.getBoard(num);
		System.out.println("deleteBoard ::: "+board);
		DeleteBoardDto dto = new DeleteBoardDto(board);
		System.out.println("dtodto : "+dto);
		if (board == null) throw new ShopException("삭제할 게시물이 없습니다.", "list?boardid=1");
		model.addAttribute("deleteBoardDto", dto);
		model.addAttribute("boardName", getBoardName(board.getBoardid()));
		model.addAttribute("num", num);
		model.addAttribute("boardid", boardid);
		return "board/delete";
	}

	@GetMapping("reply")
	public String replyForm(@RequestParam int num, Model model) {
		Board parent = service.getBoard(num);
		if (parent == null) throw new ShopException("답글을 작성할 게시물이 없습니다.", "list?boardid=1");
		BoardDto dto = new BoardDto(parent);
		dto.setTitle("RE:"+dto.getTitle());
		dto.setContent(null);
		dto.setWriter(null);
		model.addAttribute("boardDto", dto);
		model.addAttribute("boardName", getBoardName(parent.getBoardid()));
		return "board/reply";
	}

	@PostMapping("update")
	public String update(@Valid @ModelAttribute("boardDto") BoardDto board ,BindingResult bresult,Model model,HttpServletRequest request) throws Exception {
		System.out.println("PostUpdate");
		if(bresult.hasErrors()) {
			return "board/update";
		}
		int num = board.getNum();
		System.out.println("num : "+num);
		Board dbBoard = service.getBoard(num);
		System.out.println("dbBoard ::: "+dbBoard);
		if(!board.getPass().equals(dbBoard.getPass())) {
			throw new ShopException("비밀번호가틀립니다", "update?num="+board.getNum()+
					"&boardid="+dbBoard.getBoardid());
		}
		//입력값 정상+비밀번호일치
		try {
			System.out.println("boardDto : "+board);
			service.boardUpdate(board,request);
			return "redirect:detail?num="+board.getNum();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ShopException("업데이트실패", "update?num="+board.getNum()+
					"&boardid="+dbBoard.getBoardid());
		}
	}

	@PostMapping("delete")
	public String delete(@ModelAttribute DeleteBoardDto dto, Model model) {

		Board dbBoard = service.getBoard(dto.getNum());
		if(!dbBoard.getPass().equals(dto.getPass())) {
			throw new ShopException("비밀번호가틀립니다", "delete?num="+dbBoard.getNum()+
					"&boardid="+dbBoard.getBoardid());
		}
		service.boardDelete(dto);
		return "redirect:list?boardid="+dbBoard.getBoardid();
	}

	@PostMapping("reply")
	public String callReply(@Valid BoardDto board, BindingResult bresult,Model model) {
		System.out.println("board :: "+board);
		String boardid = board.getBoardid();
		int num = board.getNum();
		System.out.println("boardid"+boardid);
		System.out.println("num"+num);
		if(bresult.hasErrors()) {
			return "board/reply";
		}
		try {
			Board board1 = new Board(board);
			service.boardReply(board1);
			return "redirect:list?boardid="+boardid;
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("boardDto",board);
			String url = "board/reply";
			throw new ShopException("오류발생", url);
			}
	}

	@PostMapping("comment")
	public String comment(@Valid @ModelAttribute CommentDto comm, BindingResult bresult, Model model) {
		if(bresult.hasErrors()) {
			return commDetail(comm, model);
		}
		//comment테이블의 기본키값 , num,seq
		int seq = service.comMaxSeq(comm.getNum());
		comm.setSeq(++seq);
		Comment entity = commentMapper.toEntity(comm);//dto->domain
		service.comInsert(entity);
		return "redirect:detail?num="+comm.getNum()+"#comment";
		//#comment : id속성값이 comment위치로 시작
	}


	private String commDetail(CommentDto comm,Model model) {
		int num = comm.getNum();
		Board board = service.getBoard(num);
		String boardName = null;
		String boardid = board.getBoardid();
		if(boardid==null) {
			boardid="1";
		}
		switch(board.getBoardid()) {
		   case "1" -> boardName = "공지사항";
		   case "2" -> boardName = "자유게시판";
		   case "3" -> boardName = "Q&A";
		}
		//댓글목록 화면에전달
		List<Comment> commlist = service.commentList(num);
		comm.setNum(num);
		System.out.println("com :: "+comm);

		model.addAttribute("board",board);
		model.addAttribute("boardName",boardName);
		model.addAttribute("commlist",commlist);
		model.addAttribute("commentDto",comm);
		return "board/detail";
	}


	@PostMapping("commdel")
	public String comment(@ModelAttribute CommentDto comm,Model model) {
		Comment dbComm = service.commSelectOne(comm.getNum(),comm.getSeq());
		if(comm.getPass().equals(dbComm.getPass())) {
			service.commDel(comm.getNum(),comm.getSeq());
		}
		else {
			throw new ShopException("댓글삭제실패", "detail?num="+comm.getNum()+"#comment");
		}
		return "redirect:detail?num="+comm.getNum()+"#comment";
	}
	
	
}