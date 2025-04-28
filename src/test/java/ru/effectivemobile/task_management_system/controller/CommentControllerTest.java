package ru.effectivemobile.task_management_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;
import ru.effectivemobile.task_management_system.dto.CommentRequest;
import ru.effectivemobile.task_management_system.dto.CommentResponse;
import ru.effectivemobile.task_management_system.dto.CommentUpdateRequest;
import ru.effectivemobile.task_management_system.util.TestDataIT;

import java.security.Principal;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON_VALUE = "application/json";

    @Test
    void shouldReadAllComments() throws Exception {
        //given
        int pageNumber = 0;
        int pageSize = 3;
        CommentResponse commentResponse = TestDataIT.generateCommentResponse();

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/comments")
                .param("pageNumber", String.valueOf(pageNumber))
                .param("pageSize", String.valueOf(pageSize));

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.length()").value(3),
                        jsonPath("$[0].id").value(commentResponse.id().toString()),
                        jsonPath("$[0].text").value(commentResponse.text()),
                        jsonPath("$[0].authorId").value(commentResponse.authorId().toString()),
                        jsonPath("$[0].taskId").value(commentResponse.taskId().toString())
                );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReadCommentById_whenCommentExist() throws Exception {
        //given
        UUID commentId = UUID.fromString("10000000-0000-0000-0000-000000000001");
        CommentResponse commentResponse = TestDataIT.generateCommentResponse();

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/comments/{id}", commentId);

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(commentResponse.id().toString()),
                        jsonPath("$.text").value(commentResponse.text()),
                        jsonPath("$.authorId").value(commentResponse.authorId().toString()),
                        jsonPath("$.taskId").value(commentResponse.taskId().toString())
                );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotReadCommentById_whenCommentNotExist() throws Exception {
        //given
        UUID wrongId = UUID.fromString("10000000-0000-0000-0000-000000000091");

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/comments/{id}", wrongId);

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateComment() throws Exception {
        //given
        CommentRequest commentRequest = TestDataIT.generateCommentRequest();
        CommentResponse commentResponse = TestDataIT.generateCommentResponse();
        String jsonCommentRequest = objectMapper.writeValueAsString(commentRequest);
        UUID authorId = commentResponse.authorId();

        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName())
                .thenReturn(authorId.toString());

        MockHttpServletRequestBuilder requestBuilder = post("/api/v1/comments")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .content(jsonCommentRequest)
                .with(request -> {
                    request.setUserPrincipal(mockPrincipal);
                    return request;
                });

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.text").value(commentResponse.text()),
                        jsonPath("$.authorId").value(commentResponse.authorId().toString()),
                        jsonPath("$.taskId").value(commentResponse.taskId().toString())
                );
    }

    @Test
    void shouldUpdateComment() throws Exception {
        //given
        CommentUpdateRequest commentUpdateRequest = TestDataIT.generateCommentUpdateRequest();
        CommentResponse commentResponse = TestDataIT.generateCommentResponse();
        String jsonCommentRequest = objectMapper.writeValueAsString(commentUpdateRequest);

        MockHttpServletRequestBuilder requestBuilder = put("/api/v1/comments")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .content(jsonCommentRequest);

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(commentResponse.id().toString()),
                        jsonPath("$.text").value(commentResponse.text()),
                        jsonPath("$.authorId").value(commentResponse.authorId().toString()),
                        jsonPath("$.taskId").value(commentResponse.taskId().toString())
                );
    }

    @Test
    void shouldDeleteCommentById() throws Exception {
        //given
        UUID commentId = UUID.fromString("10000000-0000-0000-0000-000000000001");

        MockHttpServletRequestBuilder requestBuilder = delete("/api/v1/comments/{id}", commentId);

        //when, then
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }
}