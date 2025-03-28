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
import ru.effectivemobile.task_management_system.entity.common.TaskPriority;
import ru.effectivemobile.task_management_system.entity.common.TaskStatus;

import java.util.UUID;

public class TestDataIT {
    public static TaskResponse generateTaskResponse() {
        return TaskResponse.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .title("Разработать API")
                .description("Создать REST API для системы задач")
                .taskStatus(TaskStatus.IN_PROGRESS)
                .taskPriority(TaskPriority.HIGH)
                .authorId(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .performerId(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"))
                .build();
    }

    public static TaskRequest generateTaskRequest() {
        return TaskRequest.builder()
                .title("Разработать API")
                .description("Создать REST API для системы задач")
                .taskStatus(TaskStatus.IN_PROGRESS)
                .taskPriority(TaskPriority.HIGH)
                .performerId(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"))
                .build();
    }

    public static TaskUpdateRequest generateTaskUpdateRequest() {
        return TaskUpdateRequest.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .title("Разработать API")
                .description("Создать REST API для системы задач")
                .taskStatus(TaskStatus.IN_PROGRESS)
                .taskPriority(TaskPriority.HIGH)
                .performerId(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"))
                .build();
    }

    public static TaskStatusUpdateRequest generateTaskStatusUpdateRequest() {
        return TaskStatusUpdateRequest.builder()
                .taskStatus(TaskStatus.COMPLETED)
                .build();
    }

    public static TaskPriorityUpdateRequest generateTaskPriorityUpdateRequest() {
        return TaskPriorityUpdateRequest.builder()
                .taskPriority(TaskPriority.LOW)
                .build();
    }

    public static TaskPerformerUpdateRequest generateTaskPerformerUpdateRequest() {
        return TaskPerformerUpdateRequest.builder()
                .performerId(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbc"))
                .build();
    }

    public static CommentResponse generateCommentResponse() {
        return CommentResponse.builder()
                .id(UUID.fromString("10000000-0000-0000-0000-000000000001"))
                .text("Нужно добавить документацию")
                .authorId(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .taskId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .build();
    }

    public static CommentRequest generateCommentRequest() {
        return CommentRequest.builder()
                .text("Нужно добавить документацию")
                .taskId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .build();
    }

    public static CommentUpdateRequest generateCommentUpdateRequest() {
        return CommentUpdateRequest.builder()
                .id(UUID.fromString("10000000-0000-0000-0000-000000000001"))
                .text("Нужно добавить документацию")
                .taskId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .build();
    }
}
