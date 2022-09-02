package com.todoapp.service;

import com.todoapp.constants.ErrorMessages;
import com.todoapp.dto.request.LoginRequestDto;
import com.todoapp.dto.response.LoginResponseDto;
import com.todoapp.exceptions.TodoAppException;
import com.todoapp.jwt.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SessionServiceImpl implements SessionService {

    AuthenticationManager authenticationManager;
    JwtUtil jwtUtil;

    @Override
    @Cacheable("UserLogin")
    public LoginResponseDto login(LoginRequestDto loginDTO) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateJwtToken(authentication);
            return new LoginResponseDto(jwt);
        } catch (BadCredentialsException ex) {
            throw new TodoAppException(ErrorMessages.INCORRECT_USER_CREDENTIALS, ex);
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) new SecurityContextLogoutHandler().logout(request, response, auth);
    }
}
