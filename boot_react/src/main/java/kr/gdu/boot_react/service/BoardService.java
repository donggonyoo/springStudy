package kr.gdu.boot_react.service;

import jakarta.activation.DataContentHandler;
import kr.gdu.boot_react.entity.BoardEntity;
import kr.gdu.boot_react.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public int boardCount(String boardid) {
        Specification<BoardEntity> spec = (root,query,cri)->
            cri.equal(root.get("boardid"),boardid);
        //where boardid = :boardid
        return (int)boardRepository.count(spec);
    }


    public Page<BoardEntity> boardList(Integer pageInt, int limit, String boardid) {
        Specification<BoardEntity> spec = (root,query,cri)->
                cri.equal(root.get("boardid"),boardid);
        Pageable pageable = PageRequest.of(pageInt-1 , limit, Sort.by(Sort.Order.desc("num")));
        return boardRepository.findAll(spec,pageable);
    }

    public void insert(Map<String, String> param) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardid(param.get("boardid"));
        boardEntity.setPass(param.get("pass"));
        boardEntity.setSubject(param.get("subject"));
        boardEntity.setName(param.get("name"));
        boardEntity.setContent(param.get("content"));
        boardEntity.setFile1(param.get("file2"));
        boardRepository.save(boardEntity);
    }
}
