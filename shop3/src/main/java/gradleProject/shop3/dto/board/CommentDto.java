package gradleProject.shop3.dto.board;

import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

public class CommentDto {
    private int num;
    private int seq;
    @NotEmpty(message="작성자를 입력하세요")
    private String writer;
    @NotEmpty(message="비밀번호를 입력하세요")
    private String pass;
    @NotEmpty(message="내용을 입력하세요")
    private String content;
    private Date regdate;
}
