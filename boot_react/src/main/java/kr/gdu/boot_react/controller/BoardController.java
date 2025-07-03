package kr.gdu.boot_react.controller;

import ch.qos.logback.core.util.StringUtil;
import kr.gdu.boot_react.entity.BoardEntity;
import kr.gdu.boot_react.repository.BoardRepository;
import kr.gdu.boot_react.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board")
//react(front-end)가 존재하는 서버(3000)를 입력해줌
//allowCredentials : 인증의 요청도 허용
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RequiredArgsConstructor
public class BoardController {

private final BoardService boardService;

    @GetMapping("boardList")
    public Map<String,Object> boardList(@RequestParam Map<String,String> param) {
        System.out.println("param :  " +param);
        Integer pageInt = null;
        for(String key : param.keySet()){
            if(StringUtil.isNullOrEmpty(key)){
                param.put(key, null);
            }
        }
        if(param.get("pageNum") != null){
            pageInt = Integer.parseInt(param.get("pageNum"));
        }
        else{
            pageInt = 1;
        }
        String boardid = param.get("boardid");
        System.out.println("boardid : "+boardid);
        if(boardid == null){
            boardid = "1";
        }
        String boardName = null;
        switch (boardid) {
            case "1"->boardName = "공지사항";
            case "2"->boardName = "자유게시판";
            case "3"->boardName = "Q&A";
        }
        int limit = 10;
        int boardCount =boardService.boardCount(boardid); //전체게시물건수
        List<BoardEntity> bList = boardService.boardList(pageInt,limit,boardid).getContent();
        int bottomLine = 10; //한페이지의 페이지번호 갯수
        int start = (pageInt - 1) /bottomLine * bottomLine+1;
        int end  = start+bottomLine - 1;
        int maxpage = (boardCount / bottomLine) + (boardCount % bottomLine == 0 ? 0 : 1);
        if(end > maxpage){
            end = maxpage;
        }
        return Map.of(
                "boardid",boardid,
                "boardName",boardName,
                "pageInt",pageInt,
                "maxPage",maxpage,
                "start",start,
                "end",end,
                "boardCount",boardCount,
                "bList",bList,
                "bottomLine",bottomLine
        );
    }

    @PostMapping("boardPro")
    public void boardPro(@RequestParam Map<String,String> param) {
       boardService.insert(param);
        System.out.println("boardPro Post");
        System.out.println("접근완료");
        System.out.println(param);
    }
}
