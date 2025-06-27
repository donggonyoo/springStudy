package gradleProject.shop3.domain;

import java.util.Date;

import gradleProject.shop3.dto.board.BoardDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="board")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Board {

	@Id
	private int num;

	private String boardid;
	@NotBlank(message="작성자 입력하세요")
	private String writer;
	@NotBlank(message="비밀번호 입력하세요")
	private String pass;
	@NotBlank(message="제목 입력하세요")
	private String title;
	@NotBlank(message="내용 입력하세요")
	private String content;

	private String fileurl;
	private Date regdate;
	private int readcnt;
	private int grp;
	private int grplevel;
	private int grpstep;
	private int commentCnt;

	public Board(BoardDto dto){
		this.num = dto.getNum();
		this.boardid = dto.getBoardid();
		this.writer = dto.getWriter();
		this.pass = dto.getPass();
		this.title = dto.getTitle();
		this.content = dto.getContent();
		this.fileurl = dto.getFileurl();
		this.regdate = dto.getRegdate();
		this.readcnt = dto.getReadcnt();
		this.grp = dto.getGrp();
		this.grplevel = dto.getGrplevel();
		this.grpstep = dto.getGrpstep();
		this.commentCnt = dto.getCommentCnt();
	}
}
