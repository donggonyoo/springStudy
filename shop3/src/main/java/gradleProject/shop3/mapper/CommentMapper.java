package gradleProject.shop3.mapper;

import gradleProject.shop3.domain.Comment;
import gradleProject.shop3.dto.board.CommentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toDto(Comment comment);

    Comment toEntity(CommentDto commentDto);

}
