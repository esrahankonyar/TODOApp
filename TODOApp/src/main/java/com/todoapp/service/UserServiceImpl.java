package com.todoapp.service;

import com.todoapp.constants.ErrorMessages;
import com.todoapp.dto.request.UserRequestDto;
import com.todoapp.dto.response.UserResponseDto;
import com.todoapp.entity.User;
import com.todoapp.exceptions.TodoAppException;
import com.todoapp.mapper.UserMapper;
import com.todoapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByUsername(userRequestDto.getUsername()).equals(true))
            throw new TodoAppException(ErrorMessages.USER_NAME_ALREADY_EXIST);
        User user = userMapper.userDtoToUser(userRequestDto);
        userRepository.save(user);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public UserResponseDto editUser(String userId, UserRequestDto userRequestDTO) {
        User exitUser = getUserById(userId);
        User user = userMapper.userDtoToUser(userRequestDTO);
        user.setId(exitUser.getId());
        user = userRepository.save(user);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public void deleteUser(String userId) {
        getUserById(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponseDto getUser(String userId) {
        User exitUser = getUserById(userId);
        return userMapper.userToUserResponse(exitUser);
    }

    @Override
    public User findUserByUserName(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (!user.isPresent())
            throw new TodoAppException(ErrorMessages.USER_NOT_FOUND);
        return user.get();
    }

    private User getUserById(String userId) {
        Optional<User> exitUser = userRepository.findById(userId);
        if (!exitUser.isPresent())
            throw new TodoAppException(ErrorMessages.USER_NOT_FOUND);
        return exitUser.get();
    }
}
