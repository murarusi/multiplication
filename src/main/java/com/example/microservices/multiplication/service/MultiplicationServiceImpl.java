package com.example.microservices.multiplication.service;

import com.example.microservices.multiplication.domain.Multiplication;
import com.example.microservices.multiplication.domain.MultiplicationResultAttempt;
import com.example.microservices.multiplication.domain.User;
import com.example.microservices.multiplication.event.EventDispatcher;
import com.example.microservices.multiplication.event.MultiplicationSolvedEvent;
import com.example.microservices.multiplication.repository.MultiplicationRepository;
import com.example.microservices.multiplication.repository.MultiplicationResultAttemptRepository;
import com.example.microservices.multiplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService{


    private final RandomGeneratorService randomGeneratorService;
    private final MultiplicationResultAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final MultiplicationRepository multiplicationRepository;
    private final EventDispatcher eventDispatcher;

    @Autowired
    public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService,
                                     MultiplicationResultAttemptRepository attemptRepository,
                                     UserRepository userRepository,
                                     MultiplicationRepository multiplicationRepository,
                                     EventDispatcher eventDispatcher) {


        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.multiplicationRepository = multiplicationRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Override
    @Transactional
    public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
        // Check if the user already exists for that alias
        Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

        //check if multiplication exists already
        Optional<Multiplication> multiplication = multiplicationRepository.findByFactorAAndFactorB(
                attempt.getMultiplication().getFactorA(),
                attempt.getMultiplication().getFactorB()
        );
        // Avoids 'hack' attempts
        Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");

        // Check if the attempt is correct
        boolean isCorrect = attempt.getResultAttempt() ==
                attempt.getMultiplication().getFactorA() *
                        attempt.getMultiplication().getFactorB();

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(user.orElse(attempt.getUser()),
                multiplication.orElse(attempt.getMultiplication()),
                attempt.getResultAttempt(),
                isCorrect);

        // Stores the attempt
        attemptRepository.save(checkedAttempt);

        // Communicates the result via Event
        eventDispatcher.send(new MultiplicationSolvedEvent(checkedAttempt.getId(),
                checkedAttempt.getUser().getId(),
                checkedAttempt.isCorrect())
        );

        System.out.println(checkedAttempt.toString());


        return isCorrect;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }

    @Override
    public Optional<MultiplicationResultAttempt> getResultById(Long resultId) {
        return attemptRepository.findById(resultId);
    }
}
