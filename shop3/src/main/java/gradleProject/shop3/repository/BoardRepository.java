package gradleProject.shop3.repository;

import gradleProject.shop3.domain.Board;
import gradleProject.shop3.domain.Comment;
import jdk.jfr.Registered;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

//JpaSpecificationExecutor<Board> : 쿼리를 위한 조건 사용할 수 있는 권한
@Repository
public interface BoardRepository  extends JpaRepository<Board, Integer> , JpaSpecificationExecutor<Board> {
    @Query("select coalesce(max(b.num),0) from Board b  ")
    int maxNum();

    @Transactional
    @Modifying//쿼리가 데이터를 수정함을 명시.
    @Query("update Board b set b.readcnt = b.readcnt+1 where b.num=:num")
    void addReadcnt(@Param("num") int num);

    @Transactional
    @Modifying
    @Query("update Board set grpstep=grpstep+1 "
            + " where grp=:grp and grpstep > :grpstep")
    void grpStepAdd(@Param("grp") int grp ,  @Param("grpstep") int grpstep);


    @Query("select b.writer as writer , count(b) cnt  from Board b where b.boardid=:id " +
            " group by b.writer order by cnt DESC")
    List<Map<String, Object>> graph1(String id);

    @Query(value = "select date_format(coalesce(b.regdate, now()), '%Y-%m-%d') as day, count(*) as cnt " +
            "from Board b where b.boardid = :boardid " +
            "group by date_format(coalesce(b.regdate, now()), '%Y-%m-%d') " +
            "order by day desc limit 7", nativeQuery = true)
    List<Map<String, Object>> graph2(@Param("boardid") String boardid);



}
