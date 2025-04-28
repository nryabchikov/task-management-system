package ru.effectivemobile.task_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.effectivemobile.task_management_system.entity.Task;
import ru.effectivemobile.task_management_system.exception.TaskNotFoundException;
import ru.effectivemobile.task_management_system.repository.TaskRepository;
import ru.effectivemobile.task_management_system.service.TaskAccessService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskAccessServiceImpl implements TaskAccessService {

    private final TaskRepository taskRepository;

    @Override
    public boolean isPerformer(UUID taskId, String userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> TaskNotFoundException.byId(taskId));
        return UUID.fromString(userId).equals(task.getPerformerId());
    }

    @Override
    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
