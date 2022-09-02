package com.todoapp.service;

import com.todoapp.dto.request.LoginRequestDto;
import com.todoapp.dto.response.LoginResponseDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SessionService {

    LoginResponseDto login(LoginRequestDto loginDTO);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
