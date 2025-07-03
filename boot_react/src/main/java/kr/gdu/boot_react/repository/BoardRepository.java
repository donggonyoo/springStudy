package kr.gdu.boot_react.repository;

import kr.gdu.boot_react.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository  extends JpaRepository<BoardEntity, Integer>
        , JpaSpecificationExecutor<BoardEntity> {

    int countByBoardid(String boardid);
}
