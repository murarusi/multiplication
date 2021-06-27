package com.example.microservices.multiplication.repository;

import com.example.microservices.multiplication.domain.MultiplicationResultAttempt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface MultiplicationResultAttemptRepository extends CrudRepository<MultiplicationResultAttempt, Long> {

     List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);

}