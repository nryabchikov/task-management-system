package ru.effectivemobile.task_management_system.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.effectivemobile.task_management_system.dto.CommentRequest;
import ru.effectivemobile.task_management_system.dto.CommentResponse;
import ru.effectivemobile.task_management_system.dto.CommentUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskResponse;
import ru.effectivemobile.task_management_system.entity.Comment;
import ru.effectivemobile.task_management_system.entity.Task;
import ru.effectivemobile.task_management_system.exception.CommentNotFoundException;
import ru.effectivemobile.task_management_system.exception.TaskNotFoundException;
import ru.effectivemobile.task_management_system.mapper.CommentMapper;
import ru.effectivemobile.task_management_system.repository.CommentRepository;
import ru.effectivemobile.task_management_system.repository.TaskRepository;
import ru.effectivemobile.task_management_system.util.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    CommentMapper commentMapper;

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    void shouldCreateComment_whenTaskExist() {
        //given
        UUID authorId = UUID.randomUUID();
        Task task = TestData.generateTask();
        Comment comment = TestData.generateComment();
        CommentRequest commentRequest = TestData.generateCommentRequest();
        CommentResponse commentResponse = TestData.generateCommentResponse();

        when(taskRepository.findById(commentRequest.taskId()))
                .thenReturn(Optional.of(task));
        when(commentMapper.toComment(commentRequest))
                .thenReturn(comment);
        when(commentRepository.save(comment))
                .thenReturn(comment);
        when(commentMapper.toCommentResponse(comment))
                .thenReturn(commentResponse);

        //when
        CommentResponse actualCommentResponse = commentService.create(commentRequest, authorId);

        //then
        assertEquals(commentResponse, actualCommentResponse);
        verify(taskRepository).findById(any());
        verify(commentMapper).toComment(any());
        verify(commentRepository).save(any());
        verify(commentMapper).toCommentResponse(any());
    }

    @Test
    void shouldNotCreateComment_whenTaskNotExist() {
        //given
        UUID authorId = UUID.randomUUID();
        CommentRequest commentRequest = TestData.generateCommentRequest();

        when(taskRepository.findById(commentRequest.taskId()))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(
                TaskNotFoundException.class,
                () -> commentService.create(commentRequest, authorId)
        );
    }

    @Test
    void shouldReadAllComments() {
        //given
        int pageNumber = 0;
        int pageSize = 3;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Comment> comments = TestData.generateListOfComments();
        List<CommentResponse> commentResponses = new ArrayList<>();

        when(commentRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(comments, pageRequest, comments.size()));

        for (Comment comment: comments) {
            CommentResponse commentResponse = TestData.generateCommentResponse();
            when(commentMapper.toCommentResponse(comment))
                    .thenReturn(commentResponse);
            commentResponses.add(commentResponse);
        }

        //when
        Page<CommentResponse> actualCommentResponses = commentService.readAll(pageNumber, pageSize);

        //then
        assertEquals(commentResponses, actualCommentResponses.getContent());
        verify(commentRepository).findAll(any(PageRequest.class));
        verify(commentMapper, times(comments.size())).toCommentResponse(any());
    }

    @Test
    void shouldReadById_whenCommentExist() {
        //given
        UUID id = UUID.randomUUID();
        Comment comment = TestData.generateComment();
        CommentResponse commentResponse = TestData.generateCommentResponse();

        when(commentRepository.findById(id))
                .thenReturn(Optional.of(comment));
        when(commentMapper.toCommentResponse(comment))
                .thenReturn(commentResponse);

        //when
        CommentResponse actualCommentResponse = commentService.readById(id);

        //then
        assertEquals(commentResponse, actualCommentResponse);
        verify(commentRepository).findById(any());
        verify(commentMapper).toCommentResponse(any());
    }

    @Test
    void shouldNotReadById_whenCommentNotExist() {
        //given
        UUID id = UUID.randomUUID();

        when(commentRepository.findById(id))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(
                CommentNotFoundException.class,
                () -> commentService.readById(id)
        );
    }

    @Test
    void shouldUpdateComment_whenCommentAndTaskExist() {
        //given
        Task task = TestData.generateTask();
        Comment comment = TestData.generateComment();
        CommentResponse commentResponse = TestData.generateCommentResponse();
        CommentUpdateRequest commentUpdateRequest = TestData.generateCommentUpdateRequest();

        when(commentRepository.findById(commentUpdateRequest.id()))
                .thenReturn(Optional.of(comment));
        when(taskRepository.findById(commentUpdateRequest.taskId()))
                .thenReturn(Optional.of(task));
        when(commentMapper.toCommentResponse(comment))
                .thenReturn(commentResponse);

        //when
        CommentResponse actualCommentResponse = commentService.update(commentUpdateRequest);

        //then
        assertEquals(commentResponse, actualCommentResponse);
        verify(commentRepository).findById(any());
        verify(taskRepository).findById(any());
        verify(commentMapper).toCommentResponse(any());
    }

    @Test
    void shouldNotUpdateComment_whenCommentNotExist() {
        //given
        CommentUpdateRequest commentUpdateRequest = TestData.generateCommentUpdateRequest();

        when(commentRepository.findById(commentUpdateRequest.id()))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(
                CommentNotFoundException.class,
                () -> commentService.update(commentUpdateRequest)
        );
    }

    @Test
    void shouldNotUpdateComment_whenTaskNotExist() {
        //given
        CommentUpdateRequest commentUpdateRequest = TestData.generateCommentUpdateRequest();
        Comment comment = TestData.generateComment();

        when(commentRepository.findById(commentUpdateRequest.id()))
                .thenReturn(Optional.of(comment));
        when(taskRepository.findById(commentUpdateRequest.taskId()))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(
                TaskNotFoundException.class,
                () -> commentService.update(commentUpdateRequest)
        );
    }

    @Test
    void deleteCommentById() {
        //given
        UUID id = UUID.randomUUID();

        //when
        commentService.deleteById(id);

        //then
        verify(commentRepository).deleteById(id);
    }
}