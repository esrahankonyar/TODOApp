package com.todoapp.repository;

import com.todoapp.entity.Todo;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends CouchbaseRepository<Todo, String> {

    Boolean existsByIdAndUserId(String id, String userId);

    Optional<Todo> findByIdAndUserId(String id, String userId);

    List<Todo> findAllByUserId(String userId);

}