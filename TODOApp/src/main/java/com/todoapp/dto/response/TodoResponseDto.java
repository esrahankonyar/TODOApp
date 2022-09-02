package com.todoapp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TodoResponseDto {
    @JsonProperty("id")
    @NotBlank
    String id;
    @Schema(name = "title", example = "Shopping List", required = true)
    @JsonProperty("title")
    @NotBlank
    String title;
    @JsonProperty("description")
    @Schema(name = "description", example = "Milk, Bread")
    String description;
    @JsonProperty("isCompleted")
    @NotNull
    Boolean isCompleted;
}
