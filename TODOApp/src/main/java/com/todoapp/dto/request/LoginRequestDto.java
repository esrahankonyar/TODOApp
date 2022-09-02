package com.todoapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LoginRequestDto {

    @JsonProperty("username")
    @NotBlank
    String username;
    @JsonProperty("password")
    @NotBlank
    String password;

}
