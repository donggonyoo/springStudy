package gradleProject.shop3.repository;

import gradleProject.shop3.domain.Board;
import jdk.jfr.Registered;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//JpaSpecificationExecutor<Board> : 쿼리를 위한 조건 사용할 수 있는 권한
@Repository
public interface BoardRepository  extends JpaRepository<Board, Integer> , JpaSpecificationExecutor<Board> {
    @Query("select coalesce(max(b.num),0) from Board b  ")
    int maxNum();

    @Transactional
    @Modifying//쿼리가 데이터를 수정함을 명시.
    @Query("update Board b set b.readcnt = b.readcnt+1 where b.num=:num")
    void addReadcnt(@Param("num") int num);
}
