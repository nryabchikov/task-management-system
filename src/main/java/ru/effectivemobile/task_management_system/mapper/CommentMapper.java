package ru.effectivemobile.task_management_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.effectivemobile.task_management_system.dto.CommentRequest;
import ru.effectivemobile.task_management_system.dto.CommentResponse;
import ru.effectivemobile.task_management_system.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "task.id", source = "taskId")
    Comment toComment(CommentRequest commentRequest);

    @Mapping(target = "taskId", source = "task.id")
    CommentResponse toCommentResponse(Comment comment);
}
