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
    @Schema(name = "name", example = "Esra", required = true)
    String name;

    @JsonProperty("surname")
    @NotBlank
    @Schema(name = "surname", example = "Konyar", required = true)
    String surname;

    @JsonProperty("username")
    @NotBlank
    @Schema(name = "username", example = "esra", required = true)
    String username;

    @Schema(name = "password", example = "12345678", required = true)
    @JsonProperty("password")
    @NotBlank
    String password;

}
