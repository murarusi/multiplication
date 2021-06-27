package com.example.microservices.multiplication.repository;

import com.example.microservices.multiplication.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {


    Optional<User> findByAlias(String alias);
}