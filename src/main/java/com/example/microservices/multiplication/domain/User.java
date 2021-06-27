package com.example.microservices.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class User {


    private final String alias;

    // Empty constructor for JSON/JPA
    protected User() {
        alias = null;
    }
}
