package gradleProject.shop3.repository;

import gradleProject.shop3.domain.Board;
import gradleProject.shop3.domain.Comment;
import gradleProject.shop3.domain.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//키를 두개쓸거임(CommentId 내의 속성으로)
@Repository
public interface CommRepository extends JpaRepository<Comment, CommentId> {
}
