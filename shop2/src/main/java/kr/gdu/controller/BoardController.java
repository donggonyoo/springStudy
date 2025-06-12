package kr.gdu.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import kr.gdu.logic.Board;
import kr.gdu.service.BoardService;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	private  BoardService service;
	
	@GetMapping("*")
	public ModelAndView write() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(new Board());
		return mav;
	}
	
	/*
	 * spring에서 파라미터 전달 방식
	 * 1.파라미터이름과 매개변수의 이름이 같은경우 매핑
	 * 2.Bean 클래스의 프로퍼티명과 파라미터이름이 같은경우 매핑
	 * 3.Map객체에 @RequestParam 이용한매핑
	 * 	@RequestParam : 파라미터값을 Map객체에 매핑해 전달
	 */
	
	@GetMapping("list")
	public ModelAndView list(@RequestParam Map<String,String> param
						,HttpSession session) {
		//System.out.println("parrrrrram : "+param);		
		Integer pageNum = null;
				
		Set<String> keySet = param.keySet();
		for (String key : keySet) {
			if(param.get(key)==null || param.get(key).trim().equals("")) {
				param.put(key, null);
			}			
		}
		if(param.get("pageNum")!=null) {
			pageNum = Integer.parseInt(param.get("pageNum"));
		}
		else {
			pageNum=1;
		}
		String boardid = param.get("boardid");
		String searchtype = param.get("searchtype");
		String searchcontent = param.get("searchcontent");
		
		ModelAndView mav = new ModelAndView();		
		String boardName =null;
		
		switch (boardid) {
		case "1"-> boardName="공지사항";
		case "2"->boardName="자유게시판";
		case "3"->boardName="Q&A";
		default ->{boardName="공지사항";
					boardid="1";}
		}
		//게시판조회 처리
		int limit = 10;
		int listcount = service.boardcount(boardid,searchtype,searchcontent);
		List<Board> boardlist = 
				service.boardlist(pageNum,limit,boardid,searchtype,searchcontent);
		int maxpage = (int)((double)listcount/limit+0.95);
		int startpage = (int)((pageNum/10.0+0.9)-1)*10+1;
		int endpage = startpage+9;
		if(endpage>maxpage) {
			//endpage가 꼭 9이상이 아닐수도있단소리
			endpage = maxpage;
		}
		int boardno = listcount-(pageNum-1)*limit;
		mav.addObject("boardid",boardid);
		mav.addObject("boardName",boardName);
		mav.addObject("pageNum",pageNum);
		mav.addObject("maxpage",maxpage);
		mav.addObject("startpage",startpage);
		mav.addObject("endpage",endpage);
		mav.addObject("listcount",listcount);
		mav.addObject("boardlist",boardlist);
		mav.addObject("boardno",boardno);		
		return  mav;
		
	}
	

}
