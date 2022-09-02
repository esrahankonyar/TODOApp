package com.todoapp.controller;

import com.todoapp.dto.request.LoginRequestDto;
import com.todoapp.dto.response.LoginResponseDto;
import com.todoapp.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/login")
    public LoginResponseDto creteToken(@RequestBody LoginRequestDto loginRequestDto) {
        return sessionService.login(loginRequestDto);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        sessionService.logout(httpServletRequest, httpServletResponse);
    }

}
