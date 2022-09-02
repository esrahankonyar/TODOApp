package com.todoapp.service;

import com.todoapp.dto.request.TodoEditRequestDto;
import com.todoapp.dto.request.TodoRequestDto;
import com.todoapp.dto.response.TodoResponseDto;

import java.util.List;

public interface TodoService {

    TodoResponseDto createTodo(TodoRequestDto todoRequestDto);

    TodoResponseDto editTodo(String todoId, TodoEditRequestDto todoEditRequestDto);

    void deleteTodo(String userId, String todoId);

    TodoResponseDto getTodo(String todoId, String userId);

    List<TodoResponseDto> getAllUserTodoList(String userId);

    TodoResponseDto completeTodo(String todoId);

}