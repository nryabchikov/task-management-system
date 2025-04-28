package ru.effectivemobile.task_management_system.service;

import org.springframework.data.domain.Page;
import ru.effectivemobile.task_management_system.dto.TaskPerformerUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskPriorityUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskRequest;
import ru.effectivemobile.task_management_system.dto.TaskResponse;
import ru.effectivemobile.task_management_system.dto.TaskStatusUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskUpdateRequest;

import java.util.UUID;

public interface TaskService {

    TaskResponse create(TaskRequest taskRequest, UUID authorId);

    Page<TaskResponse> readAll(int pageNumber, int pageSize);

    TaskResponse readById(UUID id);

    Page<TaskResponse> readByAuthorId(int pageNumber, int pageSize, UUID authorId);

    Page<TaskResponse> readByPerformerId(int pageNumber, int pageSize, UUID performerId);

    TaskResponse update(TaskUpdateRequest taskUpdateRequest);

    void deleteById(UUID id);

    TaskResponse changeStatus(UUID id, TaskStatusUpdateRequest taskStatusUpdateRequest);

    TaskResponse changePriority(UUID id, TaskPriorityUpdateRequest taskPriorityUpdateRequest);

    TaskResponse changePerformer(UUID id, TaskPerformerUpdateRequest taskPerformerUpdateRequest);
}
