package gradleProject.shop3.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PwSearchDto {
    @NotEmpty(message = "사용자이름은 필수입니다.")
    private String userid;
    
    @NotEmpty(message = "email을 입력하세요")
    private String email;

    @NotEmpty(message = "핸드폰번호 입력하세요")
    private String phoneno;
}
