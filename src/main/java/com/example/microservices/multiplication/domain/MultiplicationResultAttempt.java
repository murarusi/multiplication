package com.example.microservices.multiplication.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class MultiplicationResultAttempt {

    @GeneratedValue
    @Id
    private Long id;


    @JoinColumn(name = "USER_ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private final User user;

    @JoinColumn(name = "MULTIPLICATION_ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private final Multiplication multiplication;
    private final int resultAttempt;

    private final boolean correct;

    // Empty constructor for JSON/JPA
    protected MultiplicationResultAttempt() {
        user = null;
        multiplication = null;
        resultAttempt = -1;
        correct = false;
    }

}