package gradleProject.shop3.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLoginDto {
    @Size(min = 3,max = 10, message = "아이디는 3자이상 10자 이하로 입력하세요")
    private String userid;

    @Size(min = 3,max = 10, message = "비밀번호는 3자이상 10하로 입력하세요")
    private String password;
}
