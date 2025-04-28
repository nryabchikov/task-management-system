package ru.effectivemobile.task_management_system.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import ru.effectivemobile.task_management_system.entity.Task;
import ru.effectivemobile.task_management_system.exception.TaskNotFoundException;
import ru.effectivemobile.task_management_system.repository.TaskRepository;
import ru.effectivemobile.task_management_system.util.TestData;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskAccessServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TaskAccessServiceImpl taskAccessService;

    @Test
    void shouldBeIsPerformer_WhenTaskExistAndUserIsPerformer() {
        //given
        UUID taskId = UUID.randomUUID();
        UUID performerId = UUID.randomUUID();

        Task task = TestData.generateTask();
        task.setPerformerId(performerId);

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(task));

        //when
        boolean result = taskAccessService.isPerformer(taskId, performerId.toString());

        //then
        assertTrue(result);
        verify(taskRepository).findById(taskId);
    }

    @Test
    void shouldNotBeIsPerformer_WhenTaskExistAndUserIsNotPerformer() {
        //given
        UUID taskId = UUID.randomUUID();
        UUID performerId = UUID.randomUUID();
        String userId = UUID.randomUUID().toString();

        Task task = new Task();
        task.setPerformerId(performerId);

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(task));

        //when
        boolean result = taskAccessService.isPerformer(taskId, userId);

        //then
        assertFalse(result);
        verify(taskRepository).findById(taskId);
    }

    @Test
    void shouldNotBeIsPerformer_WhenTaskNotFound() {
        //given
        UUID taskId = UUID.randomUUID();
        String userId = UUID.randomUUID().toString();

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(TaskNotFoundException.class, () -> {
            taskAccessService.isPerformer(taskId, userId);
        });
        verify(taskRepository).findById(taskId);
    }

    @Test
    void shouldNotBeIsAdmin_WhenNoAuthorities() {
        //given
        when(authentication.getAuthorities())
                .thenReturn(Collections.emptyList());

        //when
        boolean result = taskAccessService.isAdmin(authentication);

        //then
        assertFalse(result);
    }
}