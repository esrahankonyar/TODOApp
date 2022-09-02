package com.todoapp.mapper;

import com.todoapp.dto.request.TodoEditRequestDto;
import com.todoapp.dto.request.TodoRequestDto;
import com.todoapp.dto.response.TodoResponseDto;
import com.todoapp.entity.Todo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TodoMapper {

    @Mapping(target = "isCompleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    Todo todoRequestToTodo(TodoRequestDto todoRequestDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "isCompleted", source = "isCompleted")
    TodoResponseDto todoToTodoResponse(Todo todo);

    @Mapping(target = "isCompleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    Todo todoEditRequestToTodo(TodoEditRequestDto todoEditRequestDto);
}
