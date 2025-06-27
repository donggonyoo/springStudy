package gradleProject.shop3.dto.board;

import gradleProject.shop3.domain.Board;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
public class BoardDto {
	@Id
	private int num;
	private String boardid;
	@NotEmpty(message="작성자 입력하세요")
	private String writer;
	@NotEmpty(message="비밀번호 입력하세요")
	private String pass;
	@NotEmpty(message="제목 입력하세요")
	private String title;
	@NotEmpty(message="내용 입력하세요")
	private String content;
	private MultipartFile file1;
	private String fileurl;
	private Date regdate;
	private int readcnt;
	private int grp;
	private int grplevel;
	private int grpstep;
	private int commentCnt;

	public BoardDto(Board board) {
		this.num = board.getNum();
		this.boardid = board.getBoardid();
		this.writer = board.getWriter();
		this.pass = board.getPass();
		this.title = board.getTitle();
		this.content = board.getContent();
		this.fileurl= board.getFileurl();
		this.regdate = board.getRegdate();
		this.readcnt = board.getReadcnt();
		this.grp = board.getGrp();
		this.grplevel = board.getGrplevel();
		this.grpstep = board.getGrpstep();
		this.commentCnt = board.getCommentCnt();
	}
	
}
