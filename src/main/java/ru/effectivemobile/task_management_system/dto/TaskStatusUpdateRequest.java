package ru.effectivemobile.task_management_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.effectivemobile.task_management_system.entity.common.TaskStatus;
import ru.effectivemobile.task_management_system.util.EnumPattern;

@Builder
public record TaskStatusUpdateRequest(
        @EnumPattern(regexp = "PENDING|IN_PROGRESS|COMPLETED")
        @NotNull(message = "Status should not be null.") TaskStatus taskStatus
) {
}
