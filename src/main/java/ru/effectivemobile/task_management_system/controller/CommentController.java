package ru.effectivemobile.task_management_system.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.task_management_system.dto.CommentRequest;
import ru.effectivemobile.task_management_system.dto.CommentResponse;
import ru.effectivemobile.task_management_system.dto.CommentUpdateRequest;
import ru.effectivemobile.task_management_system.service.CommentService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> readAll(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.readAll(pageNumber, pageSize).getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("@taskAccessServiceImpl.isAdmin(authentication) || @taskAccessServiceImpl.isPerformer(#id, authentication.name)")
    public ResponseEntity<CommentResponse> readById(@PathVariable("id") @Valid @NotNull UUID id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.readById(id));
    }

    @PostMapping
    @PreAuthorize("@taskAccessServiceImpl.isAdmin(authentication) || @taskAccessServiceImpl.isPerformer(#commentRequest.taskId(), authentication.name)")
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid CommentRequest commentRequest, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.create(commentRequest, UUID.fromString(principal.getName())));
    }

    @PutMapping
    public ResponseEntity<CommentResponse> update(@RequestBody @Valid CommentUpdateRequest commentUpdateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.update(commentUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") @Valid @NotNull UUID id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }
}
