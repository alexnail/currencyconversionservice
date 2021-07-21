package com.alexnail.currencyconversionservice.util;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferAmountCalculatorTest {

    private final TransferAmountCalculator calculator = new TransferAmountCalculator();

    @Test
    void testCalculateReceiveAmount() {
        /*assertEquals(BigDecimal.ONE, calculator.calculateReceive(BigDecimal.ONE, BigDecimal.ONE, 0.0));
        assertEquals(BigDecimal.valueOf(99), calculator.calculateReceive(BigDecimal.valueOf(100), BigDecimal.ONE, 1.0));
        assertThat(BigDecimal.valueOf(88.18),
                Matchers.comparesEqualTo(calculator.calculateReceive(BigDecimal.valueOf(100), BigDecimal.valueOf(0.8818), 0.0)));
        assertEquals(BigDecimal.valueOf(97), calculator.calculateReceive(BigDecimal.valueOf(100), BigDecimal.ONE, 3.0));*/
        //assertEquals(BigDecimal.valueOf(0.42), calculator.calculateReceive(BigDecimal.ONE, BigDecimal.valueOf(0.500), 15.0));
        assertEquals(BigDecimal.valueOf(113.62), calculator.calculateReceive(BigDecimal.valueOf(25.25), BigDecimal.valueOf(5), 10.0));
    }

    @Test
    void testCalculateReverseSend() {
        /*assertEquals(BigDecimal.ONE, calculator.calculate(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, 0.0));
        assertEquals(BigDecimal.valueOf(101), calculator.calculate(BigDecimal.ZERO, BigDecimal.valueOf(100), BigDecimal.ONE, 1.0));
        assertThat(BigDecimal.valueOf(100),
                Matchers.comparesEqualTo(calculator.calculate(BigDecimal.ZERO, BigDecimal.valueOf(88.18), BigDecimal.valueOf(0.8818), 0.0)));
        assertThat(BigDecimal.valueOf(100),
                Matchers.comparesEqualTo(calculator.calculate(BigDecimal.ZERO, BigDecimal.valueOf(97), BigDecimal.ONE, 3.0)));*/
        /*assertThat(BigDecimal.ONE,
                Matchers.comparesEqualTo(calculator.calculate(BigDecimal.ZERO, BigDecimal.valueOf(0.42), BigDecimal.valueOf(0.500), 15.0)));*/
        assertThat(BigDecimal.valueOf(25.25),
                Matchers.comparesEqualTo(calculator.calculateReverseSend(BigDecimal.valueOf(113.62), BigDecimal.valueOf(5), 10.0)));
    }
}