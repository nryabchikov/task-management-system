package ru.effectivemobile.task_management_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.effectivemobile.task_management_system.dto.TaskPerformerUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskPriorityUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskRequest;
import ru.effectivemobile.task_management_system.dto.TaskResponse;
import ru.effectivemobile.task_management_system.dto.TaskStatusUpdateRequest;
import ru.effectivemobile.task_management_system.dto.TaskUpdateRequest;
import ru.effectivemobile.task_management_system.util.TestDataIT;

import java.security.Principal;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(scripts = "classpath:db/data.sql")
class TaskControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON_VALUE = "application/json";


    @Test
    void shouldReadAllTasks() throws Exception {
        //given
        int pageNumber = 0;
        int pageSize = 3;
        TaskResponse taskResponse = TestDataIT.generateTaskResponse();

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/tasks")
                .param("pageNumber", String.valueOf(pageNumber))
                .param("pageSize", String.valueOf(pageSize));

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.length()").value(3),
                        jsonPath("$[0].id").value(taskResponse.id().toString()),
                        jsonPath("$[0].title").value(taskResponse.title()),
                        jsonPath("$[0].description").value(taskResponse.description()),
                        jsonPath("$[0].taskStatus").value(taskResponse.taskStatus().toString()),
                        jsonPath("$[0].taskPriority").value(taskResponse.taskPriority().toString()),
                        jsonPath("$[0].authorId").value(taskResponse.authorId().toString()),
                        jsonPath("$[0].performerId").value(taskResponse.performerId().toString())
                );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReadTaskById_whenTaskExist() throws Exception {
        //given
        UUID taskId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        TaskResponse taskResponse = TestDataIT.generateTaskResponse();

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/tasks/{id}", taskId);

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(taskResponse.id().toString()),
                        jsonPath("$.title").value(taskResponse.title()),
                        jsonPath("$.description").value(taskResponse.description()),
                        jsonPath("$.taskStatus").value(taskResponse.taskStatus().toString()),
                        jsonPath("$.taskPriority").value(taskResponse.taskPriority().toString()),
                        jsonPath("$.authorId").value(taskResponse.authorId().toString()),
                        jsonPath("$.performerId").value(taskResponse.performerId().toString())
                );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotReadTaskById_whenTaskNotExist() throws Exception {
        //given
        UUID wrongId = UUID.fromString("11111111-1111-1111-1111-111111111112");

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/tasks/{id}", wrongId);

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateTask() throws Exception {
        //given
        TaskRequest taskRequest = TestDataIT.generateTaskRequest();
        TaskResponse taskResponse = TestDataIT.generateTaskResponse();
        String jsonTaskRequest = objectMapper.writeValueAsString(taskRequest);
        UUID authorId = taskResponse.authorId();

        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName())
                .thenReturn(authorId.toString());

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/tasks")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .content(jsonTaskRequest)
                .with(request -> {
                    request.setUserPrincipal(mockPrincipal);
                    return request;
                });

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.title").value(taskResponse.title()),
                        jsonPath("$.description").value(taskResponse.description()),
                        jsonPath("$.taskStatus").value(taskResponse.taskStatus().toString()),
                        jsonPath("$.taskPriority").value(taskResponse.taskPriority().toString()),
                        jsonPath("$.authorId").value(taskResponse.authorId().toString()),
                        jsonPath("$.performerId").value(taskResponse.performerId().toString())
                );
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //given
        TaskUpdateRequest taskUpdateRequest = TestDataIT.generateTaskUpdateRequest();
        TaskResponse taskResponse = TestDataIT.generateTaskResponse();
        String jsonTaskRequest = objectMapper.writeValueAsString(taskUpdateRequest);

        MockHttpServletRequestBuilder requestBuilder = put("/api/v1/tasks")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .content(jsonTaskRequest);

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(taskResponse.id().toString()),
                        jsonPath("$.title").value(taskResponse.title()),
                        jsonPath("$.description").value(taskResponse.description()),
                        jsonPath("$.taskStatus").value(taskResponse.taskStatus().toString()),
                        jsonPath("$.taskPriority").value(taskResponse.taskPriority().toString()),
                        jsonPath("$.authorId").value(taskResponse.authorId().toString()),
                        jsonPath("$.performerId").value(taskResponse.performerId().toString())
                );
    }

    @Test
    void shouldDeleteTaskById() throws Exception {
        //given
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");

        MockHttpServletRequestBuilder requestBuilder = delete("/api/v1/tasks/{id}", id);

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldChangeTaskStatus() throws Exception {
        //given
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        TaskStatusUpdateRequest taskStatusUpdateRequest = TestDataIT.generateTaskStatusUpdateRequest();
        TaskResponse taskResponse = TestDataIT.generateTaskResponse();
        String jsonTaskRequest = objectMapper.writeValueAsString(taskStatusUpdateRequest);

        MockHttpServletRequestBuilder requestBuilder = patch("/api/v1/tasks/{id}/status", id)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .content(jsonTaskRequest);

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.title").value(taskResponse.title()),
                        jsonPath("$.description").value(taskResponse.description()),
                        jsonPath("$.taskStatus").value(taskStatusUpdateRequest.taskStatus().toString()),
                        jsonPath("$.taskPriority").value(taskResponse.taskPriority().toString()),
                        jsonPath("$.authorId").value(taskResponse.authorId().toString()),
                        jsonPath("$.performerId").value(taskResponse.performerId().toString())
                );
    }

    @Test
    void shouldChangeTaskPriority() throws Exception {
        //given
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        TaskPriorityUpdateRequest taskPriorityUpdateRequest = TestDataIT.generateTaskPriorityUpdateRequest();
        TaskResponse taskResponse = TestDataIT.generateTaskResponse();
        String jsonTaskRequest = objectMapper.writeValueAsString(taskPriorityUpdateRequest);

        MockHttpServletRequestBuilder requestBuilder = patch("/api/v1/tasks/{id}/priority", id)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .content(jsonTaskRequest);

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.title").value(taskResponse.title()),
                        jsonPath("$.description").value(taskResponse.description()),
                        jsonPath("$.taskStatus").value(taskResponse.taskStatus().toString()),
                        jsonPath("$.taskPriority").value(taskPriorityUpdateRequest.taskPriority().toString()),
                        jsonPath("$.authorId").value(taskResponse.authorId().toString()),
                        jsonPath("$.performerId").value(taskResponse.performerId().toString())
                );
    }

    @Test
    void shouldChangeTaskPerformer() throws Exception {
        //given
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        TaskPerformerUpdateRequest taskPerformerUpdateRequest = TestDataIT.generateTaskPerformerUpdateRequest();
        TaskResponse taskResponse = TestDataIT.generateTaskResponse();
        String jsonTaskRequest = objectMapper.writeValueAsString(taskPerformerUpdateRequest);

        MockHttpServletRequestBuilder requestBuilder = patch("/api/v1/tasks/{id}/performer", id)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .content(jsonTaskRequest);

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.title").value(taskResponse.title()),
                        jsonPath("$.description").value(taskResponse.description()),
                        jsonPath("$.taskStatus").value(taskResponse.taskStatus().toString()),
                        jsonPath("$.taskPriority").value(taskResponse.taskPriority().toString()),
                        jsonPath("$.authorId").value(taskResponse.authorId().toString()),
                        jsonPath("$.performerId").value(taskPerformerUpdateRequest.performerId().toString())
                );
    }

    @Test
    void shouldReadTaskByAuthor() throws Exception {
        //given
        int pageNumber = 0;
        int pageSize = 3;
        UUID authorId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        TaskResponse taskResponse = TestDataIT.generateTaskResponse();

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/tasks/by-author/{authorId}", authorId)
                .param("pageNumber", String.valueOf(pageNumber))
                .param("pageSize", String.valueOf(pageSize));

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.length()").value(3),
                        jsonPath("$[0].id").value(taskResponse.id().toString()),
                        jsonPath("$[0].title").value(taskResponse.title()),
                        jsonPath("$[0].description").value(taskResponse.description()),
                        jsonPath("$[0].taskStatus").value(taskResponse.taskStatus().toString()),
                        jsonPath("$[0].taskPriority").value(taskResponse.taskPriority().toString()),
                        jsonPath("$[0].authorId").value(taskResponse.authorId().toString()),
                        jsonPath("$[0].performerId").value(taskResponse.performerId().toString())
                );
    }

    @Test
    void shouldReadTaskByPerformer() throws Exception {
        //given
        int pageNumber = 0;
        int pageSize = 3;
        UUID performerId = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
        TaskResponse taskResponse = TestDataIT.generateTaskResponse();

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/tasks/by-performer/{performerId}", performerId)
                .param("pageNumber", String.valueOf(pageNumber))
                .param("pageSize", String.valueOf(pageSize));

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.length()").value(2),
                        jsonPath("$[0].id").value(taskResponse.id().toString()),
                        jsonPath("$[0].title").value(taskResponse.title()),
                        jsonPath("$[0].description").value(taskResponse.description()),
                        jsonPath("$[0].taskStatus").value(taskResponse.taskStatus().toString()),
                        jsonPath("$[0].taskPriority").value(taskResponse.taskPriority().toString()),
                        jsonPath("$[0].authorId").value(taskResponse.authorId().toString()),
                        jsonPath("$[0].performerId").value(taskResponse.performerId().toString())
                );
    }
}