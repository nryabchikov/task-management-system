package ru.effectivemobile.task_management_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.effectivemobile.task_management_system.entity.common.TaskPriority;
import ru.effectivemobile.task_management_system.util.EnumPattern;

@Builder
public record TaskPriorityUpdateRequest(
        @EnumPattern(regexp = "HIGH|MEDIUM|LOW")
        @NotNull(message = "Status should not be null.") TaskPriority taskPriority
) {
}
