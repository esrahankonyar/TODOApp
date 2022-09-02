package com.todoapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TodoEditRequestDto {

    @JsonProperty("title")
    @Schema(name = "title", example = "Shopping List", required = true)
    @NotNull
    String title;
    @JsonProperty("description")
    @Schema(name = "description", example = "Milk, Bread")
    String description;

}
