package gradleProject.shop3.domain;

import java.util.Date;

import gradleProject.shop3.dto.board.CommentDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="comment")
@IdClass(CommentId.class) //id는 CommentId이다!
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comment {

	@Id
	private int num;
	@Id
	private int seq;
	private String writer;
	private String pass;
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	private Date regdate;

	@PrePersist
	public void prePersist() {
		this.regdate = new Date();
	}
	
}
