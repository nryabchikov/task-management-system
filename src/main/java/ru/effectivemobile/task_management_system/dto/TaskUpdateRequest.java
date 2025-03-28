package ru.effectivemobile.task_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.effectivemobile.task_management_system.entity.common.TaskPriority;
import ru.effectivemobile.task_management_system.entity.common.TaskStatus;

import java.util.UUID;

@Builder
public record TaskUpdateRequest(
        @NotNull(message = "Id should not be null.") UUID id,
        @NotBlank(message = "Title should not be blank.") String title,
        @NotBlank(message = "Description should not be blank.") String description,
        @NotNull(message = "Status should not be null.") TaskStatus taskStatus,
        @NotNull(message = "Priority should not be null.") TaskPriority taskPriority,
        UUID performerId
) {
}
