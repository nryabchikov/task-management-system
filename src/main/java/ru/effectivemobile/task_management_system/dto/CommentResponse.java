package ru.effectivemobile.task_management_system.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CommentResponse(
        UUID id,
        String text,
        UUID authorId,
        UUID taskId
) {
}
