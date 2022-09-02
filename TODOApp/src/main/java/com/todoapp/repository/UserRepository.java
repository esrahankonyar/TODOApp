package com.todoapp.repository;

import com.todoapp.entity.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.Optional;


public interface UserRepository extends CouchbaseRepository<User, String> {

    Optional<User> findUserByUsername(String username);

    Boolean existsByUsername(String username);

}
