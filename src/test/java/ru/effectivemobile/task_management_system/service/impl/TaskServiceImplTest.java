package ru.effectivemobile.task_management_system.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import ru.effectivemobile.task_management_system.util.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskMapper taskMapper;

    @InjectMocks
    TaskServiceImpl taskService;

    @Test
    void shouldCreateTask() {
        //given
        UUID authorId = UUID.randomUUID();
        Task task = TestData.generateTask();
        TaskRequest taskRequest = TestData.generateTaskRequest();
        TaskResponse taskResponse = TestData.generateTaskResponse();

        when(taskMapper.toTask(taskRequest))
                .thenReturn(task);
        when(taskRepository.save(task))
                .thenReturn(task);
        when(taskMapper.toTaskResponse(task))
                .thenReturn(taskResponse);

        //when
        TaskResponse actualTaskResponses = taskService.create(taskRequest, authorId);

        //then
        assertEquals(taskResponse, actualTaskResponses);
        verify(taskMapper).toTask(any());
        verify(taskRepository).save(any());
        verify(taskMapper).toTaskResponse(any());
    }

    @Test
    void shouldReadAllTasks() {
        //given
        int pageNumber = 0;
        int pageSize = 3;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Task> tasks = TestData.generateListOfTasks();
        List<TaskResponse> taskResponses = new ArrayList<>();

        when(taskRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(tasks, pageRequest, tasks.size()));

        for (Task task: tasks) {
            TaskResponse taskResponse = TestData.generateTaskResponse();
            when(taskMapper.toTaskResponse(task))
                    .thenReturn(taskResponse);
            taskResponses.add(taskResponse);
        }

        //when
        Page<TaskResponse> actualTaskResponses = taskService.readAll(pageNumber, pageSize);

        //then
        assertEquals(taskResponses, actualTaskResponses.getContent());
        verify(taskRepository).findAll(any(PageRequest.class));
        verify(taskMapper, times(tasks.size())).toTaskResponse(any());
    }

    @Test
    void shouldReadTaskById_whenTaskExist() {
        //given
        UUID id = UUID.randomUUID();
        Task task = TestData.generateTask();
        TaskResponse taskResponse = TestData.generateTaskResponse();

        when(taskRepository.findById(id))
                .thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponse(task))
                .thenReturn(taskResponse);

        //when
        TaskResponse actualResponse = taskService.readById(id);

        //then
        assertEquals(taskResponse, actualResponse);
        verify(taskRepository).findById(any());
        verify(taskMapper).toTaskResponse(any());
    }

    @Test
    void shouldNotReadTaskById_whenTaskNotExist() {
        //given
        UUID id = UUID.randomUUID();

        when(taskRepository.findById(id))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.readById(id)
        );
    }

    @Test
    void shouldReadTaskByAuthorId() {
        //given
        int pageNumber = 0;
        int pageSize = 3;
        UUID authorId = UUID.randomUUID();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Task> tasks = TestData.generateListOfTasks();
        List<TaskResponse> taskResponses = new ArrayList<>();

        when(taskRepository.findByAuthorId(authorId, pageRequest))
                .thenReturn(new PageImpl<>(tasks, pageRequest, tasks.size()));

        for (Task task: tasks) {
            TaskResponse taskResponse = TestData.generateTaskResponse();
            when(taskMapper.toTaskResponse(task))
                    .thenReturn(taskResponse);
            taskResponses.add(taskResponse);
        }

        //when
        Page<TaskResponse> actualTaskResponses = taskService.readByAuthorId(pageNumber, pageSize, authorId);

        //then
        assertEquals(taskResponses, actualTaskResponses.getContent());
        verify(taskRepository).findByAuthorId(any(), any());
        verify(taskMapper, times(tasks.size())).toTaskResponse(any());
    }

    @Test
    void shouldReadTaskByPerformerId() {
        //given
        int pageNumber = 0;
        int pageSize = 3;
        UUID performerId = UUID.randomUUID();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Task> tasks = TestData.generateListOfTasks();
        List<TaskResponse> taskResponses = new ArrayList<>();

        when(taskRepository.findByPerformerId(performerId, pageRequest))
                .thenReturn(new PageImpl<>(tasks, pageRequest, tasks.size()));

        for (Task task: tasks) {
            TaskResponse taskResponse = TestData.generateTaskResponse();
            when(taskMapper.toTaskResponse(task))
                    .thenReturn(taskResponse);
            taskResponses.add(taskResponse);
        }

        //when
        Page<TaskResponse> actualTaskResponses = taskService.readByPerformerId(pageNumber, pageSize, performerId);

        //then
        assertEquals(taskResponses, actualTaskResponses.getContent());
        verify(taskRepository).findByPerformerId(any(), any());
        verify(taskMapper, times(tasks.size())).toTaskResponse(any());
    }

    @Test
    void shouldUpdateTask_whenTaskExist() {
        //given
        Task task = TestData.generateTask();
        TaskResponse taskResponse = TestData.generateTaskResponse();
        TaskUpdateRequest taskUpdateRequest = TestData.generateTaskUpdateRequest();

        when(taskRepository.findById(taskUpdateRequest.id()))
                .thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponse(task))
                .thenReturn(taskResponse);

        //when
        TaskResponse actualTaskResponse = taskService.update(taskUpdateRequest);

        //then
        assertEquals(taskResponse, actualTaskResponse);
        verify(taskRepository).findById(any());
        verify(taskMapper).toTaskResponse(any());
    }

    @Test
    void shouldNotUpdateTask_whenTaskNotExist() {
        //given
        TaskUpdateRequest taskUpdateRequest = TestData.generateTaskUpdateRequest();

        when(taskRepository.findById(taskUpdateRequest.id()))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.update(taskUpdateRequest)
        );
    }

    @Test
    void shouldDeleteTaskById() {
        //given
        UUID id = UUID.randomUUID();

        //when
        taskService.deleteById(id);

        //then
        verify(taskRepository).deleteById(id);
    }

    @Test
    void shouldChangeTaskStatus_whenTaskExist() {
        //given
        UUID id = UUID.randomUUID();
        Task task = TestData.generateTask();
        TaskResponse taskResponse = TestData.generateTaskResponse();
        TaskStatusUpdateRequest taskStatusUpdateRequest = TestData.generateTaskStatusUpdateRequest();

        when(taskRepository.findById(id))
                .thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponse(task))
                .thenReturn(taskResponse);

        //when
        TaskResponse actualTaskResponse = taskService.changeStatus(id, taskStatusUpdateRequest);

        //then
        assertEquals(taskStatusUpdateRequest.taskStatus(), actualTaskResponse.taskStatus());
        assertEquals(taskResponse, actualTaskResponse);
        verify(taskRepository).findById(any());
        verify(taskMapper).toTaskResponse(any());
    }

    @Test
    void shouldNotChangeTaskStatus_whenTaskNotExist() {
        //given
        UUID id = UUID.randomUUID();
        TaskStatusUpdateRequest taskStatusUpdateRequest = TestData.generateTaskStatusUpdateRequest();

        when(taskRepository.findById(id))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.changeStatus(id, taskStatusUpdateRequest)
        );
    }

    @Test
    void shouldChangeTaskPriority() {
        //given
        UUID id = UUID.randomUUID();
        Task task = TestData.generateTask();
        TaskResponse taskResponse = TestData.generateTaskResponse();
        TaskPriorityUpdateRequest taskPriorityUpdateRequest = TestData.generateTaskPriorityUpdateRequest();

        when(taskRepository.findById(id))
                .thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponse(task))
                .thenReturn(taskResponse);

        //when
        TaskResponse actualTaskResponse = taskService.changePriority(id, taskPriorityUpdateRequest);

        //then
        assertEquals(taskPriorityUpdateRequest.taskPriority(), actualTaskResponse.taskPriority());
        assertEquals(taskResponse, actualTaskResponse);
        verify(taskRepository).findById(any());
        verify(taskMapper).toTaskResponse(any());
    }

    @Test
    void shouldNotChangeTaskPriority_whenTaskNotExist() {
        //given
        UUID id = UUID.randomUUID();
        TaskPriorityUpdateRequest taskPriorityUpdateRequest = TestData.generateTaskPriorityUpdateRequest();

        when(taskRepository.findById(id))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.changePriority(id, taskPriorityUpdateRequest)
        );
    }

    @Test
    void shouldChangeTaskPerformer() {
        //given
        UUID id = UUID.randomUUID();
        Task task = TestData.generateTask();
        TaskResponse taskResponse = TestData.generateTaskResponse();
        TaskPerformerUpdateRequest taskPerformerUpdateRequest = TestData.generateTaskPerformerUpdateRequest();

        when(taskRepository.findById(id))
                .thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponse(task))
                .thenReturn(taskResponse);

        //when
        TaskResponse actualTaskResponse = taskService.changePerformer(id, taskPerformerUpdateRequest);

        //then
        assertEquals(taskResponse.performerId(), actualTaskResponse.performerId());
        assertEquals(taskResponse, actualTaskResponse);
        verify(taskRepository).findById(any());
        verify(taskMapper).toTaskResponse(any());
    }

    @Test
    void shouldNotChangeTaskPerformer_whenTaskNotExist() {
        //given
        UUID id = UUID.randomUUID();
        TaskPerformerUpdateRequest taskPerformerUpdateRequest = TestData.generateTaskPerformerUpdateRequest();

        when(taskRepository.findById(id))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.changePerformer(id, taskPerformerUpdateRequest)
        );
    }
}