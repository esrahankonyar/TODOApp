package com.todoapp.service;

import com.todoapp.constants.ErrorMessages;
import com.todoapp.dto.request.UserRequestDto;
import com.todoapp.dto.response.UserResponseDto;
import com.todoapp.entity.User;
import com.todoapp.exceptions.TodoAppException;
import com.todoapp.mapper.UserMapper;
import com.todoapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("User Service Test")
class UserServiceImplTest {

    private static final String TEST_USERNAME = "TestUserName";
    private static final String TEST_PASSWORD = "Test_PASS";
    private static final String TEST_NAME = "Esra";
    private static final String TEST_SURNAME = "Konyar";
    private static final String TEST_USERID = "3e862461-d4f1-4005-8c03-516c71379675";

    private User user;
    private UserResponseDto userResponseDto;
    private UserRequestDto userRequestDto;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserMapper userMapper;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(TEST_USERID)
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .name(TEST_NAME)
                .surname(TEST_SURNAME)
                .build();

        userResponseDto = UserResponseDto.builder()
                .id(UUID.randomUUID().toString())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .build();

        userRequestDto = UserRequestDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .password(user.getPassword())
                .build();
    }

    @Test
    void createUser_trueStory() {
        //Given
        when(userMapper.userToUserResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponseDto);
        when(userMapper.userDtoToUser(ArgumentMatchers.any(UserRequestDto.class))).thenReturn(user);
        when(userRepository.existsByUsername(ArgumentMatchers.any(String.class))).thenReturn(false);
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        //When
        UserResponseDto response = userService.createUser(userRequestDto);

        //Then
        verify(userRepository, times(1)).save(user);
        assertAll(
                () -> assertNotNull(response.getUsername()),
                () -> assertNotNull(response.getId())
        );
    }

    @Test
    void createUser_whenExistUsername_shouldThrowTodoAppException() {
        //Given
        when(userRepository.existsByUsername(ArgumentMatchers.any(String.class))).thenReturn(true);

        //When & Then
        Assertions.assertThatThrownBy(() -> userService.createUser(userRequestDto)).isInstanceOf(TodoAppException.class)
                .hasMessageContaining(ErrorMessages.USER_NAME_ALREADY_EXIST);
    }

    @Test
    void editUser_trueStory() {
        //Given
        when(userMapper.userToUserResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponseDto);
        when(userMapper.userDtoToUser(ArgumentMatchers.any(UserRequestDto.class))).thenReturn(user);
        when(userRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(user));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        //When
        UserResponseDto response = userService.editUser(TEST_USERID, userRequestDto);

        //Then
        verify(userRepository, times(1)).save(user);
        assertAll(
                () -> assertEquals(response.getUsername(), userRequestDto.getUsername()),
                () -> assertEquals(response.getName(), userRequestDto.getName()),
                () -> assertEquals(response.getSurname(), userRequestDto.getSurname())
        );
    }

    @Test
    void editUser_whenNotExistUserId_shouldThrowTodoAppException() {
        //Given
        when(userRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.empty());

        //When & Then
        Assertions.assertThatThrownBy(() -> userService.editUser(TEST_USERID, userRequestDto)).isInstanceOf(TodoAppException.class)
                .hasMessageContaining(ErrorMessages.USER_NOT_FOUND);
    }

    @Test
    void deleteUser_trueStory() {
        //Given
        when(userRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(user));

        //When
        userService.deleteUser(user.getId());

        //Then
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void deleteUser_whenNotExistUserId_shouldThrowTodoAppException() {
        //Given
        when(userRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.empty());

        //When & Then
        Assertions.assertThatThrownBy(() -> userService.deleteUser(TEST_USERID)).isInstanceOf(TodoAppException.class)
                .hasMessageContaining(ErrorMessages.USER_NOT_FOUND);
    }

    @Test
    void getUser_trueStory() {
        //Given
        when(userRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponseDto);

        //When
        UserResponseDto response = userService.getUser(user.getId());

        //Then
        assertAll(
                () -> assertNotNull(response.getId()),
                () -> assertNotNull(response.getUsername()),
                () -> assertNotNull(response.getName()),
                () -> assertNotNull(response.getSurname())
        );
    }

    @Test
    void getUser_whenNotExistUserId_shouldThrowTodoAppException() {
        //Given
        when(userRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.empty());

        //When & Then
        Assertions.assertThatThrownBy(() -> userService.getUser(TEST_USERID)).isInstanceOf(TodoAppException.class)
                .hasMessageContaining(ErrorMessages.USER_NOT_FOUND);
    }

    @Test
    void findUserByUsername_trueStory() {
        //Given
        when(userRepository.findUserByUsername(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponseDto);

        //When
        User response = userService.findUserByUserName(user.getUsername());
        verify(userRepository, times(1)).findUserByUsername(user.getUsername());

        //Then
        assertAll(
                () -> assertNotNull(response.getId()),
                () -> assertNotNull(response.getUsername()),
                () -> assertNotNull(response.getName()),
                () -> assertNotNull(response.getSurname())
        );
    }

    @Test
    void findUserByUsername_whenNotExistUserName_shouldThrowTodoAppException() {
        //Given
        when(userRepository.findUserByUsername(ArgumentMatchers.any(String.class))).thenReturn(Optional.empty());

        //When & Then
        Assertions.assertThatThrownBy(() -> userService.findUserByUserName(TEST_USERNAME)).isInstanceOf(TodoAppException.class)
                .hasMessageContaining(ErrorMessages.USER_NOT_FOUND);
    }
}