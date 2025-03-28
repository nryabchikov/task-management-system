package ru.effectivemobile.task_management_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.task_management_system.entity.Task;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Page<Task> findByAuthorId(UUID authorId, Pageable pageable);
    Page<Task> findByPerformerId(UUID performerId, Pageable pageable);
}
