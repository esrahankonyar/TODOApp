package com.todoapp.mapper;

import com.todoapp.decarator.UserMapperDecorator;
import com.todoapp.dto.request.UserRequestDto;
import com.todoapp.dto.response.UserResponseDto;
import com.todoapp.entity.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "username", source = "username")
    User userDtoToUser(UserRequestDto userRequestDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "username", source = "username")
    UserResponseDto userToUserResponse(User user);

}
