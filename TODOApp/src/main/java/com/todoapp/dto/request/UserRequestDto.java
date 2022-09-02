package com.todoapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserRequestDto {

    @JsonProperty("name")
    @NotBlank
    @Schema(name = "name", example = "Foo", required = true)
    String name;

    @JsonProperty("surname")
    @NotBlank
    @Schema(name = "surname", example = "Foo", required = true)
    String surname;

    @JsonProperty("username")
    @NotBlank
    @Schema(name = "username", example = "Foo", required = true)
    String username;

    @Schema(name = "password", example = "abc12345", required = true)
    @JsonProperty("password")
    @NotBlank
    String password;

}
