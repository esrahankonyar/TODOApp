package com.todoapp.service;

import com.todoapp.constants.ErrorMessages;
import com.todoapp.dto.request.TodoEditRequestDto;
import com.todoapp.dto.request.TodoRequestDto;
import com.todoapp.dto.response.TodoResponseDto;
import com.todoapp.entity.Todo;
import com.todoapp.entity.User;
import com.todoapp.exceptions.TodoAppException;
import com.todoapp.mapper.TodoMapper;
import com.todoapp.repository.TodoRepository;
import com.todoapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Todo Service Test")
public class TodoServiceImplTest {

    private static final String TEST_TITLE = "Shopping List";
    private static final String TEST_DESCRIPTION = "Milk, Bread";
    private static final Boolean TEST_IS_COMPLETED_FALSE = Boolean.FALSE;
    private static final Boolean TEST_IS_COMPLETED_TRUE = Boolean.TRUE;

    private static final String TEST_TITLE_EDIT = "Shopping List - Edit";
    private static final String TEST_DESCRIPTION_EDIT = "Milk, Bread - Edit";

    private static final String TEST_TODOID = "3e862461-d4f1-4005-8c03-516c71379675";

    private static final String TEST_USERNAME = "TestUserName";
    private static final String TEST_PASSWORD = "Test_PASS";
    private static final String TEST_NAME = "Esra";
    private static final String TEST_SURNAME = "Konyar";
    private static final String TEST_USERID = "3e862461-d4f1-4005-8c03-516c71379861";


    private Todo todo;
    private Todo editedTodo;
    private Todo completedTodo;
    private User user;
    private TodoResponseDto todoResponseDto;
    private TodoRequestDto todoRequestDto;
    private TodoResponseDto todoEditResponseDto;
    private TodoEditRequestDto todoEditRequestDto;
    private TodoResponseDto completedTodoResponseDto;

    @InjectMocks
    TodoServiceImpl todoService;

    @Mock
    TodoMapper todoMapper;

    @Mock
    TodoRepository todoRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        todo = Todo.builder()
                .id(TEST_TODOID)
                .title(TEST_TITLE)
                .description(TEST_DESCRIPTION)
                .isCompleted(TEST_IS_COMPLETED_FALSE)
                .userId(TEST_USERID)
                .build();

        todoResponseDto = TodoResponseDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .isCompleted(todo.getIsCompleted())
                .build();

        todoRequestDto = TodoRequestDto.builder()
                .title(todo.getTitle())
                .description(todo.getDescription())
                .userId(todo.getUserId())
                .build();

        todoEditRequestDto = TodoEditRequestDto.builder()
                .title(TEST_TITLE_EDIT)
                .description(TEST_DESCRIPTION_EDIT)
                .build();

        editedTodo = Todo.builder()
                .id(TEST_TODOID)
                .title(TEST_TITLE_EDIT)
                .description(TEST_DESCRIPTION_EDIT)
                .isCompleted(TEST_IS_COMPLETED_FALSE)
                .userId(TEST_USERID)
                .build();

        todoEditResponseDto = TodoResponseDto.builder()
                .id(TEST_TODOID)
                .title(TEST_TITLE_EDIT)
                .description(TEST_DESCRIPTION_EDIT)
                .isCompleted(TEST_IS_COMPLETED_FALSE)
                .build();

        completedTodo = Todo.builder()
                .id(TEST_TODOID)
                .title(TEST_TITLE_EDIT)
                .description(TEST_DESCRIPTION_EDIT)
                .isCompleted(TEST_IS_COMPLETED_TRUE)
                .userId(TEST_USERID)
                .build();

        completedTodoResponseDto = TodoResponseDto.builder()
                .id(TEST_TODOID)
                .title(TEST_TITLE_EDIT)
                .description(TEST_DESCRIPTION_EDIT)
                .isCompleted(TEST_IS_COMPLETED_TRUE)
                .build();

        user = User.builder()
                .id(TEST_USERID)
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .name(TEST_NAME)
                .surname(TEST_SURNAME)
                .build();
    }

    @Test
    void createTodo_trueStory() {
        //Given
        when(todoMapper.todoToTodoResponse(ArgumentMatchers.any(Todo.class))).thenReturn(todoResponseDto);
        when(todoMapper.todoRequestToTodo(ArgumentMatchers.any(TodoRequestDto.class))).thenReturn(todo);
        when(userRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(user));
        when(todoRepository.save(ArgumentMatchers.any(Todo.class))).thenReturn(todo);

        //When
        TodoResponseDto response = todoService.createTodo(todoRequestDto);

        //Then
        verify(todoRepository, times(1)).save(todo);
        assertAll(
                () -> assertNotNull(response.getId())
        );
    }

    @Test
    void createTodo_whenNotExistUserId_shouldThrowTodoAppException() {
        //Given
        when(userRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.empty());

        //When & Then
        Assertions.assertThatThrownBy(() -> todoService.createTodo(todoRequestDto)).isInstanceOf(TodoAppException.class)
                .hasMessageContaining(ErrorMessages.USER_NOT_FOUND);
    }

    @Test
    void editTodo_trueStory() {
        //Given
        when(todoMapper.todoToTodoResponse(ArgumentMatchers.any(Todo.class))).thenReturn(todoEditResponseDto);
        when(todoMapper.todoEditRequestToTodo(ArgumentMatchers.any(TodoEditRequestDto.class))).thenReturn(todo);
        when(todoRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(todo));
        when(todoRepository.save(ArgumentMatchers.any(Todo.class))).thenReturn(editedTodo);

        //When
        TodoResponseDto response = todoService.editTodo(TEST_TODOID, todoEditRequestDto);

        //Then
        verify(todoRepository, times(1)).save(editedTodo);
        assertAll(
                () -> assertEquals(response.getTitle(), todoEditRequestDto.getTitle()),
                () -> assertEquals(response.getDescription(), todoEditRequestDto.getDescription())
        );
    }

    @Test
    void editTodo_whenNotExistTodoId_shouldThrowTodoAppException() {
        //Given
        when(todoRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.empty());

        //When & Then
        Assertions.assertThatThrownBy(() -> todoService.editTodo(TEST_TODOID, todoEditRequestDto)).isInstanceOf(TodoAppException.class)
                .hasMessageContaining(ErrorMessages.TODO_ITEM_NOT_FOUND);
    }

    @Test
    void deleteTodo_trueStory() {
        //Given
        when(todoRepository.existsByIdAndUserId(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .thenReturn(Boolean.TRUE);

        //When
        todoService.deleteTodo(user.getId(), todo.getId());

        //Then
        verify(todoRepository, times(1)).deleteById(todo.getId());
    }

    @Test
    void deleteTodo_whenNotExistTodoIdAndUserId_shouldThrowTodoAppException() {
        //Given
        when(todoRepository.existsByIdAndUserId(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .thenReturn(Boolean.FALSE);

        //When & Then
        Assertions.assertThatThrownBy(() -> todoService.deleteTodo(TEST_USERID, TEST_TODOID)).isInstanceOf(TodoAppException.class)
                .hasMessageContaining(ErrorMessages.TODO_ITEM_NOT_FOUND);
    }

    @Test
    void getUser_trueStory() {
        //Given
        when(todoRepository.findByIdAndUserId(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(todo));
        when(todoMapper.todoToTodoResponse(ArgumentMatchers.any(Todo.class))).thenReturn(todoResponseDto);

        //When
        TodoResponseDto response = todoService.getTodo(TEST_TODOID, TEST_USERID);

        //Then
        assertAll(
                () -> assertNotNull(response.getId()),
                () -> assertNotNull(response.getDescription()),
                () -> assertNotNull(response.getTitle()),
                () -> assertNotNull(response.getIsCompleted())
        );
    }

    @Test
    void getUser_whenNotExistTodoIdAndUserId_shouldThrowTodoAppException() {
        //Given
        when(todoRepository.findByIdAndUserId(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.empty());

        //When & Then
        Assertions.assertThatThrownBy(() -> todoService.getTodo(TEST_TODOID, TEST_USERID)).isInstanceOf(TodoAppException.class)
                .hasMessageContaining(ErrorMessages.TODO_ITEM_NOT_FOUND);
    }

    @Test
    void getAllUserTodoList_trueStory() {
        //Given
        List<Todo> todoList = new ArrayList<>();
        todoList.add(todo);
        when(todoRepository.findAllByUserId(ArgumentMatchers.any(String.class))).thenReturn(todoList);
        when(todoMapper.todoToTodoResponse(ArgumentMatchers.any(Todo.class))).thenReturn(todoResponseDto);

        //When
        List<TodoResponseDto> response = todoService.getAllUserTodoList(user.getId());
        verify(todoRepository, times(1)).findAllByUserId(user.getId());

        //Then
        assertEquals(response.size(), 1);
    }

    @Test
    void completeTodo_trueStory() {
        //Given
        when(todoMapper.todoToTodoResponse(ArgumentMatchers.any(Todo.class))).thenReturn(completedTodoResponseDto);
        when(todoRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(todo));
        when(todoRepository.save(ArgumentMatchers.any(Todo.class))).thenReturn(completedTodo);

        //When
        TodoResponseDto response = todoService.completeTodo(TEST_TODOID);

        //Then
        verify(todoRepository, times(1)).save(completedTodo);
        assertEquals(response.getIsCompleted(), Boolean.TRUE);
    }

    @Test
    void completeTodo_whenNotExistTodoId_shouldThrowTodoAppException() {
        //Given
        when(todoRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.empty());

        //When & tThen
        Assertions.assertThatThrownBy(() -> todoService.completeTodo(TEST_TODOID)).isInstanceOf(TodoAppException.class)
                .hasMessageContaining(ErrorMessages.TODO_ITEM_NOT_FOUND);
    }


}
