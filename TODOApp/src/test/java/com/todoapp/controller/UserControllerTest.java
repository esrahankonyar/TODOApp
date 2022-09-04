package com.todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.dto.request.UserRequestDto;
import com.todoapp.dto.response.UserResponseDto;
import com.todoapp.jwt.AuthEntryPoint;
import com.todoapp.jwt.JwtUtil;
import com.todoapp.service.UserDetailsServiceImpl;
import com.todoapp.service.UserService;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("User Controller Test")
public class UserControllerTest {

    private static final String TEST_USERNAME = "TestUserName";
    private static final String TEST_PASSWORD = "Test_PASS";
    private static final String TEST_NAME = "Esra";
    private static final String TEST_SURNAME = "Konyar";
    private static final String TEST_USERID = "3e862461d4f140058c03516c71379675";
    private static final String url = "/user/";

    private UserRequestDto userRequestDTO;
    private UserResponseDto userResponseDTO;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

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
        userRequestDTO = UserRequestDto.builder()
                .password(TEST_PASSWORD)
                .username(TEST_USERNAME)
                .name(TEST_NAME)
                .surname(TEST_SURNAME)
                .build();

        userResponseDTO = UserResponseDto.builder()
                .id(TEST_USERID)
                .username(TEST_USERNAME)
                .name(TEST_NAME)
                .surname(TEST_SURNAME)
                .build();
    }

    @Test
    void createUser_trueStory() throws Exception {
        //Given
        String content = objectMapper.writeValueAsString(userRequestDTO);
        given(userService.createUser(userRequestDTO)).willReturn(userResponseDTO);
        MockHttpServletRequestBuilder builder = post(url.concat("signup"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //When
        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        UserRequestDto requestContent = objectMapper.readValue(content, UserRequestDto.class);
        UserResponseDto responseContent = objectMapper.readValue(response.getContentAsString(), UserResponseDto.class);

        //Then
        assertAll(
                () -> assertEquals(201, response.getStatus()),
                () -> assertEquals(requestContent.getUsername(), responseContent.getUsername())
        );
    }

    @Test
    void editUser_trueStory() throws Exception {
        //Given
        String content = objectMapper.writeValueAsString(userRequestDTO);
        given(userService.editUser(TEST_USERID, userRequestDTO)).willReturn(userResponseDTO);
        MockHttpServletRequestBuilder builder = put(url.concat(TEST_USERID))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //When
        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        //Then
        assertEquals(200, response.getStatus());

    }

    @Test
    void deleteUser_trueStory() throws Exception {
        //Given
        String uri = url.concat(TEST_USERID);
        MockHttpServletRequestBuilder builder = delete(uri).contentType(MediaType.APPLICATION_JSON);

        //When
        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        //Then
        assertEquals(204, response.getStatus());
    }

    @Test
    void getUser_trueStory() throws Exception {
        //Given
        given(userService.getUser(TEST_USERID)).willReturn(userResponseDTO);
        String uri = url.concat(TEST_USERID);
        MockHttpServletRequestBuilder builder = get(uri).contentType(MediaType.APPLICATION_JSON);

        //When
        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        //Then
        assertEquals(200, response.getStatus());
    }
}
