package ru.effectivemobile.task_management_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TaskPerformerUpdateRequest(
        @NotNull(message = "Performer ID cannot be null")
        UUID performerId
) {
}
