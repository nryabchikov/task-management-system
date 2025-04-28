package ru.effectivemobile.task_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effectivemobile.task_management_system.dto.CommentRequest;
import ru.effectivemobile.task_management_system.dto.CommentResponse;
import ru.effectivemobile.task_management_system.dto.CommentUpdateRequest;
import ru.effectivemobile.task_management_system.entity.Comment;
import ru.effectivemobile.task_management_system.entity.Task;
import ru.effectivemobile.task_management_system.exception.CommentNotFoundException;
import ru.effectivemobile.task_management_system.exception.TaskNotFoundException;
import ru.effectivemobile.task_management_system.mapper.CommentMapper;
import ru.effectivemobile.task_management_system.repository.CommentRepository;
import ru.effectivemobile.task_management_system.repository.TaskRepository;
import ru.effectivemobile.task_management_system.service.CommentService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public CommentResponse create(CommentRequest commentRequest, UUID authorId) {
        Task task = taskRepository.findById(commentRequest.taskId()).orElseThrow(
                () -> TaskNotFoundException.byId(commentRequest.taskId())
        );
        Comment comment = commentMapper.toComment(commentRequest);
        comment.setTask(task);
        comment.setAuthorId(authorId);
        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @Override
    public Page<CommentResponse> readAll(int pageNumber, int pageSize) {
        return commentRepository.findAll(PageRequest.of(pageNumber, pageSize)).map(commentMapper::toCommentResponse);
    }

    @Override
    public CommentResponse readById(UUID id) {
        return commentMapper.toCommentResponse(
                commentRepository.findById(id).orElseThrow(() -> CommentNotFoundException.byId(id))
        );
    }

    @Override
    @Transactional
    public CommentResponse update(CommentUpdateRequest commentUpdateRequest) {
        Comment comment = commentRepository.findById(commentUpdateRequest.id()).orElseThrow(
                () -> CommentNotFoundException.byId(commentUpdateRequest.id())
        );
        Task task = taskRepository.findById(commentUpdateRequest.taskId()).orElseThrow(
                () -> TaskNotFoundException.byId(commentUpdateRequest.taskId())
        );

        comment.setText(commentUpdateRequest.text());
        comment.setTask(task);

        return commentMapper.toCommentResponse(comment);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        commentRepository.deleteById(id);
    }
}
