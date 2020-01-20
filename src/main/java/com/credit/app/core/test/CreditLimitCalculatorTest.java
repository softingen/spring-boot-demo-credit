package com.credit.app.core.test;

import com.credit.app.core.util.CreditLimitCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditLimitCalculatorTest {

    @Test
    void calculateCreditLimit() {
        try {
            assertEquals(java.util.Optional.of(0.0), java.util.Optional.of(CreditLimitCalculator.calculateCreditLimit(400, 5000.0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

