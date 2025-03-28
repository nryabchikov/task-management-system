package ru.effectivemobile.task_management_system.dto;

import lombok.Builder;
import ru.effectivemobile.task_management_system.entity.common.TaskPriority;
import ru.effectivemobile.task_management_system.entity.common.TaskStatus;

import java.util.List;
import java.util.UUID;

@Builder
public record TaskResponse(
        UUID id,
        String title,
        String description,
        TaskStatus taskStatus,
        TaskPriority taskPriority,
        UUID authorId,
        UUID performerId,
        List<CommentResponse> comments
) {
}
