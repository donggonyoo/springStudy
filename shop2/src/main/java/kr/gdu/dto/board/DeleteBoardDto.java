package kr.gdu.dto.board;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DeleteBoardDto {
	
	private int num;
	@NotEmpty(message="비밀번호를 입력해")
	private String pass;
	private String boardid;

}
