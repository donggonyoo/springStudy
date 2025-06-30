package gradleProject.shop3.repository;

import gradleProject.shop3.domain.Board;
import gradleProject.shop3.domain.Comment;
import gradleProject.shop3.domain.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//키를 두개쓸거임(CommentId 내의 속성으로)
@Repository
public interface CommRepository extends JpaRepository<Comment, CommentId> {

    List<Comment> findByNum(int num);//JPA에서 자동으로 쿼리제공

    @Query("select coalesce(max(c.seq),0) from Comment c  where c.num= :num")
    int comMaxSeq(int num);
}
