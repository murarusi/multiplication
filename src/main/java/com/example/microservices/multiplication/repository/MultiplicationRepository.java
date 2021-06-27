package com.example.microservices.multiplication.repository;

import com.example.microservices.multiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
}