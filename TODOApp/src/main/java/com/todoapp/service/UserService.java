package com.todoapp.service;

import com.todoapp.dto.request.UserRequestDto;
import com.todoapp.dto.response.UserResponseDto;
import com.todoapp.entity.User;


public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto editUser(String userId, UserRequestDto userRequestDTO);

    void deleteUser(String userId);

    UserResponseDto getUser(String userId);

    User findUserByUserName(String username);

}
