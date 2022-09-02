package com.todoapp.error;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({
            IllegalArgumentException.class,
            UsernameNotFoundException.class,
            JWTCreationException.class,
            JWTVerificationException.class,
            ConstraintViolationException.class
    })
    @ResponseStatus(BAD_REQUEST)
    ResponseEntity<ErrorModel> handleCustomBadRequestException(Exception ex, HttpServletRequest request) {
        ErrorModel response = errorDetails(ex.getMessage(), ex, BAD_REQUEST, request);
        return ResponseEntity
                .status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ExceptionHandler({
            ServletException.class,
            IOException.class,
            NullPointerException.class
    })
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorModel> handleCustomValidationException(Exception ex, HttpServletRequest request) {
        ErrorModel response = errorDetails(ex.getMessage(), ex, INTERNAL_SERVER_ERROR, request);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    private ErrorModel errorDetails(String message, Exception exception, HttpStatus httpStatus, HttpServletRequest request) {
        ErrorModel errorDetail = ErrorModel.builder()
                .message(message)
                .status(httpStatus.value())
                .timestamp(new Date())
                .error(httpStatus.getReasonPhrase())
                .path(request.getRequestURI().substring(request.getContextPath().length())).build();

        log.error(exception.getMessage());
        return errorDetail;
    }
}
