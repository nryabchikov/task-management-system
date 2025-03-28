package ru.effectivemobile.task_management_system.exception;

import java.util.UUID;

public class CommentNotFoundException extends RuntimeException {
    private CommentNotFoundException(String message) {
        super(message);
    }

    public static CommentNotFoundException byId(UUID id) {
        return new CommentNotFoundException(String.format("Comment with id %s not found", id));
    }
}
