package ru.effectivemobile.task_management_system.exception;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {
    private TaskNotFoundException(String message) {
        super(message);
    }

    public static TaskNotFoundException byId(UUID id) {
        return new TaskNotFoundException(String.format("Task with id %s not found", id));
    }
}
