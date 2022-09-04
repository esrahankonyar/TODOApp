package com.todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.dto.request.TodoEditRequestDto;
import com.todoapp.dto.request.TodoRequestDto;
import com.todoapp.dto.response.TodoResponseDto;
import com.todoapp.jwt.AuthEntryPoint;
import com.todoapp.jwt.JwtUtil;
import com.todoapp.service.TodoService;
import com.todoapp.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("Todo Controller Test")
public class TodoControllerTest {

    private static final String TEST_TITLE = "Shopping List";
    private static final String TEST_DESCRIPTION = "Milk, Bread";
    private static final Boolean TEST_IS_COMPLETED_FALSE = Boolean.FALSE;
    private static final Boolean TEST_IS_COMPLETED_TRUE = Boolean.TRUE;
    private static final String TEST_TODOID = "3e862461-d4f1-4005-8c03-516c71379675";
    private static final String TEST_USERID = "3e862461d4f140058c03516c71379675";

    private static final String TEST_TITLE_EDIT = "Shopping List - Edit";
    private static final String TEST_DESCRIPTION_EDIT = "Milk, Bread - Edit";
    private static final String url = "/todo/";

    private TodoRequestDto todoRequestDTO;
    private TodoResponseDto todoResponseDTO;
    private TodoEditRequestDto todoEditRequestDto;
    private TodoResponseDto todoEditResponseDto;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TodoService todoService;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    AuthEntryPoint authEntryPoint;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        todoRequestDTO = TodoRequestDto.builder()
                .title(TEST_TITLE)
                .userId(TEST_USERID)
                .description(TEST_DESCRIPTION)
                .build();

        todoResponseDTO = TodoResponseDto.builder()
                .id(TEST_TODOID)
                .title(TEST_TITLE)
                .description(TEST_DESCRIPTION)
                .isCompleted(false)
                .build();

        todoEditRequestDto = TodoEditRequestDto.builder()
                .title(TEST_TITLE_EDIT)
                .description(TEST_DESCRIPTION_EDIT)
                .build();

        todoEditResponseDto = TodoResponseDto.builder()
                .id(TEST_TODOID)
                .title(TEST_TITLE_EDIT)
                .description(TEST_DESCRIPTION_EDIT)
                .isCompleted(TEST_IS_COMPLETED_FALSE)
                .build();
    }

    @Test
    void createTodo_trueStory() throws Exception {
        //Given
        String content = objectMapper.writeValueAsString(todoRequestDTO);
        given(todoService.createTodo(todoRequestDTO)).willReturn(todoResponseDTO);

        MockHttpServletRequestBuilder builder = post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //When
        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        TodoRequestDto requestContent = objectMapper.readValue(content, TodoRequestDto.class);
        TodoResponseDto responseContent = objectMapper.readValue(response.getContentAsString(), TodoResponseDto.class);

        //Then
        assertAll("Verify the TODO Controller POST condition",
                () -> assertEquals(201, response.getStatus()),
                () -> assertEquals(requestContent.getDescription(), responseContent.getDescription()),
                () -> assertEquals(requestContent.getTitle(), responseContent.getTitle())
        );
    }

    @Test
    void editTodo_trueStory() throws Exception {
        //Given
        String content = objectMapper.writeValueAsString(todoEditRequestDto);
        given(todoService.editTodo(TEST_TODOID, todoEditRequestDto)).willReturn(todoEditResponseDto);

        MockHttpServletRequestBuilder builder = put(url.concat(TEST_TODOID))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //When
        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        TodoEditRequestDto requestContent = objectMapper.readValue(content, TodoEditRequestDto.class);
        TodoResponseDto responseContent = objectMapper.readValue(response.getContentAsString(), TodoResponseDto.class);

        //Then
        assertAll("Verify the TODO Controller PUT condition",
                () -> assertEquals(200, response.getStatus()),
                () -> assertEquals(requestContent.getDescription(), responseContent.getDescription()),
                () -> assertEquals(requestContent.getTitle(), responseContent.getTitle())
        );
    }

    @Test
    void deleteTodo_trueStory() throws Exception {
        //Given
        String uri = url.concat(TEST_TODOID).concat("/user/").concat(TEST_USERID);

        //When
        MockHttpServletRequestBuilder builder = delete(uri).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        //Then
        assertEquals(204, response.getStatus(), "Verify the TODO Controller DELETE condition");
    }

    @Test
    void getTodo_trueStory() throws Exception {
        //Given
        given(todoService.getTodo(TEST_TODOID, TEST_USERID)).willReturn(todoResponseDTO);

        String uri = url.concat(TEST_TODOID).concat("/user/").concat(TEST_USERID);

        //When
        MockHttpServletRequestBuilder builder = get(uri).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        //Then
        assertEquals(200, response.getStatus(), "Verify the TODO Controller Get condition");
    }

    @Test
    void getAllUserTodo_trueStory() throws Exception {
        //Given
        List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();
        todoResponseDtoList.add(todoResponseDTO);
        given(todoService.getAllUserTodoList(TEST_USERID)).willReturn(todoResponseDtoList);

        //When
        MockHttpServletRequestBuilder builder = get(url.concat(TEST_USERID)).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        //Then
        assertEquals(200, response.getStatus(), "Verify the TODO Controller Get condition");
    }

    @Test
    void completeTodo_trueStory() throws Exception {
        //Given
        String uri = url.concat(TEST_TODOID).concat("/complete");

        //When
        MockHttpServletRequestBuilder builder = put(uri).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        //Then
        assertEquals(200, response.getStatus(), "Verify the TODO Controller Get condition");
    }
}
