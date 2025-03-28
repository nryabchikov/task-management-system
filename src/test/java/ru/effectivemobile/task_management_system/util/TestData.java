package ru.effectivemobile.task_management_system.util;

import ru.effectivemobile.task_management_system.dto.CommentRequest;
import ru.effectivemobile.task_management_system.dto.CommentResponse;
import ru.effectivemobile.task_management_system.dto.CommentUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskPerformerUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskPriorityUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskRequest;
import ru.effectivemobile.task_management_system.dto.TaskResponse;
import ru.effectivemobile.task_management_system.dto.TaskStatusUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskUpdateRequest;
import ru.effectivemobile.task_management_system.entity.Comment;
import ru.effectivemobile.task_management_system.entity.Task;
import ru.effectivemobile.task_management_system.entity.common.TaskPriority;
import ru.effectivemobile.task_management_system.entity.common.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestData {
    public static Task generateTask() {
        return Task.builder()
                .id(UUID.randomUUID())
                .title("Title")
                .description("Some description")
                .taskStatus(TaskStatus.COMPLETED)
                .taskPriority(TaskPriority.HIGH)
                .authorId(UUID.randomUUID())
                .performerId(UUID.randomUUID())
                .comments(List.of())
                .build();
    }

    public static TaskRequest generateTaskRequest() {
        return TaskRequest.builder()
                .title("Title")
                .description("Some description")
                .taskStatus(TaskStatus.COMPLETED)
                .taskPriority(TaskPriority.HIGH)
                .performerId(UUID.randomUUID())
                .build();
    }

    public static TaskResponse generateTaskResponse() {
        return TaskResponse.builder()
                .id(UUID.randomUUID())
                .title("Title")
                .description("Some description")
                .taskStatus(TaskStatus.COMPLETED)
                .taskPriority(TaskPriority.HIGH)
                .authorId(UUID.randomUUID())
                .performerId(UUID.randomUUID())
                .comments(List.of())
                .build();
    }

    public static TaskUpdateRequest generateTaskUpdateRequest() {
        return TaskUpdateRequest.builder()
                .id(UUID.randomUUID())
                .title("Title")
                .description("Some description")
                .taskStatus(TaskStatus.COMPLETED)
                .taskPriority(TaskPriority.HIGH)
                .performerId(UUID.randomUUID())
                .build();
    }

    public static List<Task> generateListOfTasks() {
        int count = (int) (Math.random() * 100) + 3;
        List<Task> tasks = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            tasks.add(generateTask());
        }
        return tasks;
    }

    public static TaskStatusUpdateRequest generateTaskStatusUpdateRequest() {
        return TaskStatusUpdateRequest.builder()
                .taskStatus(TaskStatus.COMPLETED)
                .build();
    }

    public static TaskPriorityUpdateRequest generateTaskPriorityUpdateRequest() {
        return TaskPriorityUpdateRequest.builder()
                .taskPriority(TaskPriority.HIGH)
                .build();
    }

    public static TaskPerformerUpdateRequest generateTaskPerformerUpdateRequest() {
        return TaskPerformerUpdateRequest.builder()
                .performerId(UUID.randomUUID())
                .build();
    }

    public static Comment generateComment() {
        return Comment.builder()
                .id(UUID.randomUUID())
                .text("Comment text")
                .authorId(UUID.randomUUID())
                .task(generateTask())
                .build();
    }

    public static CommentRequest generateCommentRequest() {
        return CommentRequest.builder()
                .text("Comment text")
                .taskId(UUID.randomUUID())
                .build();
    }

    public static CommentResponse generateCommentResponse() {
        return CommentResponse.builder()
                .id(UUID.randomUUID())
                .text("Comment text")
                .authorId(UUID.randomUUID())
                .taskId(UUID.randomUUID())
                .build();
    }

    public static CommentUpdateRequest generateCommentUpdateRequest() {
        return CommentUpdateRequest.builder()
                .id(UUID.randomUUID())
                .text("Comment text")
                .taskId(UUID.randomUUID())
                .build();
    }

    public static List<Comment> generateListOfComments() {
        int count = (int) (Math.random() * 100) + 3;
        List<Comment> comments = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            comments.add(generateComment());
        }
        return comments;
    }
}
