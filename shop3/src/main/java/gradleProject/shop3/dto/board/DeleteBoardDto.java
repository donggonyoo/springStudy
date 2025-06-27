package gradleProject.shop3.dto.board;

import gradleProject.shop3.domain.Board;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DeleteBoardDto {
	
	private int num;
	@NotEmpty(message="비밀번호를 입력해")
	private String pass;
	private String boardid;

	public DeleteBoardDto(Board board) {
		this.num = board.getNum();
		this.pass = board.getPass();
		this.boardid = board.getBoardid();
	}

}
