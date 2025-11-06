package com.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FailSimulationService {

    private static final Logger log = LoggerFactory.getLogger(FailSimulationService.class);

    private final AtomicReference<LocalDateTime> failUntil = new AtomicReference<>(LocalDateTime.now());
    private final AtomicBoolean isFailing = new AtomicBoolean(false);

    @Value("${fail.probability:0.1}")
    private volatile double probability;
    @Value("${fail.time:5}")
    private volatile int failTime;

    public void activate(long time) {
        this.failUntil.set(LocalDateTime.now().plusSeconds(time));
        this.isFailing.set(true);
        log.warn("Modo de falhas ativado por {} minutos", time);
    }

    public void deactivate() {
        this.failUntil.set(LocalDateTime.now());
        isFailing.set(false);
        log.warn("Modo de falhas desativado");
    }

    public boolean shouldFail() {
        var now = LocalDateTime.now();
        var until = this.failUntil.get();
        if (now.isBefore(until)) {
            log.warn("Tempo restante de falha: {}", Duration.between(now, until).toSeconds());
            return true;
        }
        if (!isFailing.get()) {
            System.out.println("Now: " + LocalDateTime.now() + " - Until: " + failUntil.get());
            if (ThreadLocalRandom.current().nextDouble() < probability) {
                this.activate(failTime);
                return true;
            }
        }
        if (isFailing.get()) {
            this.deactivate();
        }
        return false;
    }

}
