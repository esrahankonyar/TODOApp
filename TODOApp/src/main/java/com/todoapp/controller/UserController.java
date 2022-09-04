package com.todoapp.controller;

import com.todoapp.dto.request.UserRequestDto;
import com.todoapp.dto.response.UserResponseDto;
import com.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create User")
    public UserResponseDto createUser(@RequestBody @Valid UserRequestDto user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Edit User")
    @SecurityRequirement(name = "Bearer Auth")
    public UserResponseDto editUser(@PathVariable String userId, @RequestBody @Valid UserRequestDto user) {
        return userService.editUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove User")
    @SecurityRequirement(name = "Bearer Auth")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get User")
    @SecurityRequirement(name = "Bearer Auth")
    public UserResponseDto getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

}
