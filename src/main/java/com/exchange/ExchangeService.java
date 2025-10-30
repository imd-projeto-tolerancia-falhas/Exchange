package com.exchange;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ExchangeService {

    private static final double MIN_VALUE = 5.0;
    private static final double MAX_VALUE = 6.0;

    public BigDecimal generate() {

        double randomDouble = ThreadLocalRandom.current().nextDouble(MIN_VALUE, MAX_VALUE);

        BigDecimal randomBigDecimal = BigDecimal.valueOf(randomDouble);

        return randomBigDecimal.setScale(2, RoundingMode.DOWN);
    }
}
