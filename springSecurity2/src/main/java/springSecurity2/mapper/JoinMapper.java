package springSecurity2.mapper;

import org.mapstruct.Mapper;
import springSecurity2.dto.JoinDto;
import springSecurity2.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface JoinMapper {
    UserEntity toUserEntity(JoinDto joinDto);
}
