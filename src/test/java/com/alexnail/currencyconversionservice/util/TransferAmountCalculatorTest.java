package com.alexnail.currencyconversionservice.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransferAmountCalculatorTest {

    private final TransferAmountCalculator calculator = new TransferAmountCalculator();

    @Test
    @DisplayName("Should return (send * rate) - ((send * rate) / commission)")
    void whenSendAmountIsPresentAndReceiveIsNot_calculateShouldReturnSendTimesRateMinusCommissionAmount() {
        assertEquals(BigDecimal.ONE, calculator.calculate(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, 0.0));
        assertEquals(BigDecimal.valueOf(99), calculator.calculate(BigDecimal.valueOf(100), BigDecimal.ZERO, BigDecimal.ONE, 1.0));
    }

    @Test
    @DisplayName("Should return (receive / rate) + ((receive / rate) / commission)")
    void whenReceiveAmountIsPresentAndSendIsNot_calculateShouldReturnReceiveDividedByRatePlusCommissionAmount() {
        assertEquals(BigDecimal.ONE, calculator.calculate(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE, 0.0));
        assertEquals(BigDecimal.valueOf(101), calculator.calculate(BigDecimal.ZERO, BigDecimal.valueOf(100), BigDecimal.ONE, 1.0));
    }
}