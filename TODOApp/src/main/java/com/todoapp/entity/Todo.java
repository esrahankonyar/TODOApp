package com.todoapp.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.io.Serializable;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Todo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @EqualsAndHashCode.Include
    String id;
    @Field
    String userId;
    @Field
    String title;
    @Field
    String description;
    @Field
    @Builder.Default
    Boolean isCompleted = false;
}

