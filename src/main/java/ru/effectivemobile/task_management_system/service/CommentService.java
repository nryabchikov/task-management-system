package ru.effectivemobile.task_management_system.service;

import org.springframework.data.domain.Page;
import ru.effectivemobile.task_management_system.dto.CommentRequest;
import ru.effectivemobile.task_management_system.dto.CommentResponse;
import ru.effectivemobile.task_management_system.dto.CommentUpdateRequest;

import java.util.UUID;

public interface CommentService {

    CommentResponse create(CommentRequest commentRequest, UUID authorId);

    Page<CommentResponse> readAll(int pageNumber, int pageSize);

    CommentResponse readById(UUID id);

    CommentResponse update(CommentUpdateRequest commentUpdateRequest);

    void deleteById(UUID id);
}
