package gradleProject.shop3.mapper;

import gradleProject.shop3.domain.User;
import gradleProject.shop3.dto.PwSearchDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PwSearchMapper {

    User toEntiy(PwSearchDto dto);
    PwSearchDto toDto(User user);

}
