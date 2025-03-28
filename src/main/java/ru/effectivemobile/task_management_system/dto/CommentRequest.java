package ru.effectivemobile.task_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CommentRequest(
        @NotBlank(message = "Title should not be blank.") String text,
        @NotNull(message = "TaskId should not be null.") UUID taskId
) {
}
