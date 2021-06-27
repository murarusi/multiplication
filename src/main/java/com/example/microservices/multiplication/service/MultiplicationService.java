package com.example.microservices.multiplication.service;

import com.example.microservices.multiplication.domain.Multiplication;
import com.example.microservices.multiplication.domain.MultiplicationResultAttempt;

import java.util.List;
import java.util.Optional;

public interface MultiplicationService {

    Multiplication createRandomMultiplication();


    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

    List<MultiplicationResultAttempt> getStatsForUser(final String userAlias);


    Optional<MultiplicationResultAttempt> getResultById(final Long resultId);


}
