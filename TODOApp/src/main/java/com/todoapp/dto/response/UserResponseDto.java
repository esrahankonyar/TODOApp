package com.todoapp.dto.response;

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
public class UserResponseDto {

    @JsonProperty("userId")
    String id;
    @JsonProperty("username")
    @Schema(name = "username", example = "Foo", required = true)
    @NotBlank
    String username;
    @JsonProperty("name")
    @Schema(name = "name", example = "foo", required = true)
    String name;
    @JsonProperty("surname")
    @Schema(name = "surname", example = "foo", required = true)
    String surname;

}
