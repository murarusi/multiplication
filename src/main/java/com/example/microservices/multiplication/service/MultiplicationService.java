package com.example.microservices.multiplication.service;

import com.example.microservices.multiplication.domain.Multiplication;
import com.example.microservices.multiplication.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationService {

    Multiplication createRandomMultiplication();


    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

    List<MultiplicationResultAttempt> getStatsForUser(final String userAlias);


    MultiplicationResultAttempt getResultById(final Long resultId);


}
