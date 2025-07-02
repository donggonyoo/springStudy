package dong.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
public class UserDto {
    private String userId;
    private String userName;
    private String password;
}
