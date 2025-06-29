package gradleProject.shop3.mapper;

import gradleProject.shop3.domain.User;
import gradleProject.shop3.dto.UserDto;
import gradleProject.shop3.dto.UserLoginDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
//User와 UserDto 간의 변환 로직을 정의합니다.
//componentModel = "spring"은 생성된 매퍼 구현체를 Spring 프레임워크의 빈(Bean)으로 등록하도록 설정합니다.
//-> Spring에서 @Autowired로 이 매퍼를 주입받아 사용할 수 있습니다.
public interface UserMapper {
    //userDto->domain
    User toEntity(UserDto userDto);

    //domain-> dto
    UserDto toDto(User user);

}
