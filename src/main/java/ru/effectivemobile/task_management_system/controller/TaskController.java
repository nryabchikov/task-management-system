package ru.effectivemobile.task_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.task_management_system.dto.CommentResponse;
import ru.effectivemobile.task_management_system.dto.TaskPerformerUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskPriorityUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskRequest;
import ru.effectivemobile.task_management_system.dto.TaskResponse;
import ru.effectivemobile.task_management_system.dto.TaskStatusUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskUpdateRequest;
import ru.effectivemobile.task_management_system.entity.common.TaskPriority;
import ru.effectivemobile.task_management_system.entity.common.TaskStatus;
import ru.effectivemobile.task_management_system.service.TaskService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * REST контроллер для управления задачами .
 * Предоставляет эндпоинты для получения (всех, по автору и по исполнителю), создания, обновления,
 * удаления задач, изменения статуса, приоритета и исполнителя задачи.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    /**
     * Сервис для получения (всех, по автору и по исполнителю), создания, обновления,
     * удаления задач, изменения статуса, приоритета и исполнителя задачи.
     */
    private final TaskService taskService;

    /**
     * Возвращает список всех задач используя пагинацию.
     *
     * @param pageNumber Номер страницы. Значение по умолчанию: 0.
     * @param pageSize   Размер страницы. Значение по умолчанию: 3.
     * @return Список задач {@link TaskResponse}.
     */
    @Operation(
            parameters = {
                    @Parameter(name = "pageNumber", description = "Номер страницы"),
                    @Parameter(name = "pageSize", description = "Размер страницы")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = TaskResponse.class
                                            )
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<TaskResponse>> readAll(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.readAll(pageNumber, pageSize).getContent());
    }

    /**
     * Возвращает задачу по её ID.
     *
     * @param id ID новости.
     * @return Задача {@link TaskResponse}.
     */
    @Operation(
            parameters = {
                    @Parameter(
                            name = "id", description = "ID задачи",
                            schema = @Schema(type = "string", format = "uuid"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = TaskResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена")
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("@taskAccessServiceImpl.isAdmin(authentication) || @taskAccessServiceImpl.isPerformer(#id, authentication.name)")
    public ResponseEntity<TaskResponse> readById(@PathVariable("id") @Valid @NotNull UUID id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.readById(id));
    }

    /**
     * Создает новую задачу.
     *
     * @param taskRequest Запрос на создание задачи.
     * @param principal   Авторизованный пользователь.
     * @return Созданная задача {@link TaskResponse}.
     */
    @Operation(
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    type = "object",
                                    example = """
                                            {
                                              "title": "Захватывающий заголовок задачи!",
                                              "description": "Супер-пупер мега крутое описание!",
                                              "taskStatus": "PENDING",
                                              "taskPriority": "LOW"
                                            }
                                            """,
                                    properties = {
                                            @StringToClassMapItem(key = "title", value = String.class),
                                            @StringToClassMapItem(key = "description", value = String.class),
                                            @StringToClassMapItem(key = "taskStatus", value = TaskStatus.class),
                                            @StringToClassMapItem(key = "taskPriority", value = TaskPriority.class)
                                    }
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            headers = @Header(name = "Content-Type", description = "Data type"),
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(
                                                    type = "object",
                                                    properties = {
                                                            @StringToClassMapItem(key = "id", value = UUID.class),
                                                            @StringToClassMapItem(key = "title", value = String.class),
                                                            @StringToClassMapItem(key = "description", value = String.class),
                                                            @StringToClassMapItem(key = "taskStatus", value = TaskStatus.class),
                                                            @StringToClassMapItem(key = "taskPriority", value = TaskPriority.class),
                                                            @StringToClassMapItem(key = "authorId", value = UUID.class),
                                                            @StringToClassMapItem(key = "performerId", value = UUID.class),
                                                            @StringToClassMapItem(key = "comments", value = List.class)
                                                    }
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = "Плохой запрос"),
                    @ApiResponse(responseCode = "422", description = "Ошибки валидации сущности"),
            })
    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody @Valid TaskRequest taskRequest,
                                               Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.create(taskRequest, UUID.fromString(principal.getName())));
    }

    /**
     * Обновляет существующую задачу.
     *
     * @param taskUpdateRequest Запрос на обновление задачи.
     * @return Обновленная задача {@link TaskResponse}.
     */
    @Operation(
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    type = "object",
                                    example = """
                                        {
                                          "id": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                                          "title": "Обновленный не такой уже заголовок",
                                          "description": "Обновленный описание",
                                          "taskStatus": "COMPLETED",
                                          "taskPriority": "LOW",
                                          "performerId": "a1b2c3d4-e5f6-7890-1234-567890abcdeg",
                                        }
                                        """,
                                    properties = {
                                            @StringToClassMapItem(key = "id", value = UUID.class),
                                            @StringToClassMapItem(key = "title", value = String.class),
                                            @StringToClassMapItem(key = "description", value = String.class),
                                            @StringToClassMapItem(key = "taskStatus", value = TaskStatus.class),
                                            @StringToClassMapItem(key = "taskPriority", value = TaskPriority.class),
                                            @StringToClassMapItem(key = "authorId", value = UUID.class),
                                            @StringToClassMapItem(key = "performerId", value = UUID.class),
                                            @StringToClassMapItem(key = "comments", value = List.class)
                                    }
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена"),
                    @ApiResponse(responseCode = "400", description = "Плохой запрос"),
                    @ApiResponse(responseCode = "422", description = "Ошибки валидации сущности"),
            }
    )
    @PutMapping
    public ResponseEntity<TaskResponse> update(@RequestBody @Valid TaskUpdateRequest taskUpdateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.update(taskUpdateRequest));
    }

    /**
     * Удаляет задачу по её ID.
     *
     * @param id ID задачи.
     * @return Ответ с кодом 204 (No Content).
     */
    @Operation(
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID задачи",
                            schema = @Schema(type = "string", format = "uuid")
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Задача успешно удалена")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") @Valid @NotNull UUID id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("@taskAccessServiceImpl.isAdmin(authentication) || @taskAccessServiceImpl.isPerformer(#id, authentication.name)")
    public ResponseEntity<TaskResponse> changeStatus(
            @PathVariable("id") @Valid @NotNull UUID id,
            @RequestBody @Valid TaskStatusUpdateRequest taskStatusUpdateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.changeStatus(id, taskStatusUpdateRequest));
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<TaskResponse> changePriority(
            @PathVariable("id") @Valid @NotNull UUID id,
            @RequestBody @Valid TaskPriorityUpdateRequest taskPriorityUpdateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.changePriority(id, taskPriorityUpdateRequest));
    }

    @PatchMapping("/{id}/performer")
    public ResponseEntity<TaskResponse> changePerformer(
            @PathVariable("id") @Valid @NotNull UUID id,
            @RequestBody @Valid TaskPerformerUpdateRequest taskPriorityUpdateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.changePerformer(id, taskPriorityUpdateRequest));
    }

    @GetMapping("/by-author/{authorId}")
    public ResponseEntity<List<TaskResponse>> readByAuthor(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @PathVariable("authorId") @Valid @NotNull UUID authorId
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.readByAuthorId(pageNumber, pageSize, authorId).getContent());
    }

    @GetMapping("/by-performer/{performerId}")
    public ResponseEntity<List<TaskResponse>> readByPerformer(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
            @PathVariable("performerId") @Valid @NotNull UUID performerId
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.readByPerformerId(pageNumber, pageSize, performerId).getContent());
    }
}
