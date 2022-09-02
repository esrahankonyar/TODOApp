package com.todoapp.controller;

import com.todoapp.dto.request.TodoEditRequestDto;
import com.todoapp.dto.request.TodoRequestDto;
import com.todoapp.dto.response.TodoResponseDto;
import com.todoapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/todo")
@SecurityRequirement(name = "Bearer Auth")
public class TodoController {

    TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create todo")
    public TodoResponseDto createTodo(@RequestBody @Valid TodoRequestDto todoRequestDTO) {
        return todoService.createTodo(todoRequestDTO);
    }

    @PutMapping("/{todoId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Edit todo")
    public TodoResponseDto editTodo(@PathVariable String todoId, @RequestBody @Valid TodoEditRequestDto todoEditRequestDto) {
        return todoService.editTodo(todoId, todoEditRequestDto);
    }

    @DeleteMapping("/{todoId}/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete todo")
    public void deleteTodo(@PathVariable String todoId,
                           @PathVariable String userId) {
        todoService.deleteTodo(userId, todoId);
    }

    @GetMapping("/{todoId}/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Todo")
    public TodoResponseDto getTodo(@PathVariable String todoId,
                                   @PathVariable String userId) {
        return todoService.getTodo(todoId, userId);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all todo")
    public List<TodoResponseDto> getAllUserTodo(@PathVariable String userId) {
        return todoService.getAllUserTodoList(userId);
    }

    @PutMapping("/{todoId}/complete")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Complete to todo")
    public TodoResponseDto completeTodo(@PathVariable String todoId) {
        return todoService.completeTodo(todoId);
    }
}

