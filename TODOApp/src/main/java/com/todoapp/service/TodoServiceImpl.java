package com.todoapp.service;

import com.todoapp.constants.ErrorMessages;
import com.todoapp.dto.request.TodoEditRequestDto;
import com.todoapp.dto.request.TodoRequestDto;
import com.todoapp.dto.response.TodoResponseDto;
import com.todoapp.entity.Todo;
import com.todoapp.exceptions.TodoAppException;
import com.todoapp.mapper.TodoMapper;
import com.todoapp.repository.TodoRepository;
import com.todoapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TodoServiceImpl implements TodoService {

    TodoRepository todoRepository;
    UserRepository userRepository;
    TodoMapper todoMapper;

    @Override
    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto) {
        if (!userRepository.findById(todoRequestDto.getUserId()).isPresent())
            throw new TodoAppException(ErrorMessages.USER_NOT_FOUND);
        Todo todo = todoMapper.todoRequestToTodo(todoRequestDto);
        todo = todoRepository.save(todo);
        return todoMapper.todoToTodoResponse(todo);
    }

    @Override
    public TodoResponseDto editTodo(String todoId, TodoEditRequestDto todoEditRequestDto) {
        Optional<Todo> existTodo = getTodoById(todoId);
        Todo todo = todoMapper.todoEditRequestToTodo(todoEditRequestDto);
        todo.setId(todoId);
        todo.setUserId(existTodo.get().getUserId());
        todo = todoRepository.save(todo);
        return todoMapper.todoToTodoResponse(todo);
    }

    @Override
    public void deleteTodo(String userId, String todoId) {
        if (!todoRepository.existsByIdAndUserId(todoId, userId))
            throw new TodoAppException(ErrorMessages.TODO_ITEM_NOT_FOUND);
        todoRepository.deleteById(todoId);
    }

    @Override
    public TodoResponseDto getTodo(String todoId, String userId) {
        Optional<Todo> existTodo = todoRepository.findByIdAndUserId(todoId, userId);
        if (!existTodo.isPresent())
            throw new TodoAppException(ErrorMessages.TODO_ITEM_NOT_FOUND);
        return todoMapper.todoToTodoResponse(existTodo.get());
    }

    @Override
    public List<TodoResponseDto> getAllUserTodoList(String userId) {
        List<Todo> todoList = todoRepository.findAllByUserId(userId);
        return todoList.stream()
                .map(todoMapper::todoToTodoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponseDto completeTodo(String todoId) {
        Optional<Todo> existTodo = getTodoById(todoId);
        existTodo.get().setIsCompleted(true);
        Todo todo = todoRepository.save(existTodo.get());
        return todoMapper.todoToTodoResponse(todo);
    }

    private Optional<Todo> getTodoById(String todoId) {
        Optional<Todo> existTodo = todoRepository.findById(todoId);
        if (!existTodo.isPresent())
            throw new TodoAppException(ErrorMessages.TODO_ITEM_NOT_FOUND);
        return existTodo;
    }
}