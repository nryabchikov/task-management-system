package ru.effectivemobile.task_management_system.service;

import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface TaskAccessService {

    boolean isPerformer(UUID taskId, String userId);

    boolean isAdmin(Authentication authentication);
}
