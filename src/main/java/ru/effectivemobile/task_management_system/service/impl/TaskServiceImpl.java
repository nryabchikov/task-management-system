package ru.effectivemobile.task_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effectivemobile.task_management_system.dto.TaskPerformerUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskPriorityUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskRequest;
import ru.effectivemobile.task_management_system.dto.TaskResponse;
import ru.effectivemobile.task_management_system.dto.TaskStatusUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskUpdateRequest;
import ru.effectivemobile.task_management_system.entity.Task;
import ru.effectivemobile.task_management_system.exception.TaskNotFoundException;
import ru.effectivemobile.task_management_system.mapper.TaskMapper;
import ru.effectivemobile.task_management_system.repository.TaskRepository;
import ru.effectivemobile.task_management_system.service.TaskService;

import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse create(TaskRequest taskRequest, UUID authorId) {
        Task task = taskMapper.toTask(taskRequest);
        task.setAuthorId(authorId);
        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    @Override
    public Page<TaskResponse> readAll(int pageNumber, int pageSize) {
        return taskRepository.findAll(PageRequest.of(pageNumber, pageSize)).map(taskMapper::toTaskResponse);
    }

    @Override
    public TaskResponse readById(UUID id) {
        return taskMapper.toTaskResponse(
                taskRepository.findById(id).orElseThrow(() -> TaskNotFoundException.byId(id))
        );
    }

    @Override
    public Page<TaskResponse> readByAuthorId(int pageNumber, int pageSize, UUID authorId) {
        return taskRepository.findByAuthorId(authorId, PageRequest.of(pageNumber, pageSize))
                .map(taskMapper::toTaskResponse);
    }

    @Override
    public Page<TaskResponse> readByPerformerId(int pageNumber, int pageSize, UUID performerId) {
        return taskRepository.findByPerformerId(performerId, PageRequest.of(pageNumber, pageSize))
                .map(taskMapper::toTaskResponse);
    }

    @Override
    @Transactional
    public TaskResponse update(TaskUpdateRequest taskUpdateRequest) {
        Task task = taskRepository.findById(taskUpdateRequest.id()).orElseThrow(
                () -> TaskNotFoundException.byId(taskUpdateRequest.id())
        );

        task.setTitle(taskUpdateRequest.title());
        task.setDescription(taskUpdateRequest.description());
        task.setTaskStatus(taskUpdateRequest.taskStatus());
        task.setTaskPriority(taskUpdateRequest.taskPriority());
        task.setPerformerId(taskUpdateRequest.performerId());

        return taskMapper.toTaskResponse(task);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TaskResponse changeStatus(UUID id, TaskStatusUpdateRequest taskStatusUpdateRequest) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> TaskNotFoundException.byId(id)
        );

        task.setTaskStatus(taskStatusUpdateRequest.taskStatus());

        return taskMapper.toTaskResponse(task);
    }

    @Override
    @Transactional
    public TaskResponse changePriority(UUID id, TaskPriorityUpdateRequest taskPriorityUpdateRequest) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> TaskNotFoundException.byId(id)
        );

        task.setTaskPriority(taskPriorityUpdateRequest.taskPriority());

        return taskMapper.toTaskResponse(task);
    }

    @Override
    @Transactional
    public TaskResponse changePerformer(UUID id, TaskPerformerUpdateRequest taskPerformerUpdateRequest) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> TaskNotFoundException.byId(id)
        );

        task.setPerformerId(taskPerformerUpdateRequest.performerId());

        return taskMapper.toTaskResponse(task);
    }
}
