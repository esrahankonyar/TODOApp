package com.todoapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoAppExceptionModel {

    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;
    private List<Map<String, String>> errors;

}
