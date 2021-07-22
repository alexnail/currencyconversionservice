package com.alexnail.currencyconversionservice.util;

import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


class TransferAmountCalculatorTest {

    private final TransferAmountCalculator calculator = new TransferAmountCalculator();

    @Test
    void testCalculateReceiveAmount() {
        assertAll(
                () -> assertThat(calculator.calculateReceive(ONE, ONE, 0d), comparesEqualTo(ONE)),
                () -> assertThat(calculator.calculateReceive(valueOf(100), ONE, 1d), comparesEqualTo(valueOf(99))),
                () -> assertThat(calculator.calculateReceive(valueOf(100), valueOf(0.8818), 0d), comparesEqualTo(valueOf(88.18))),
                () -> assertThat(calculator.calculateReceive(valueOf(100), ONE, 3d), comparesEqualTo(valueOf(97))),
                () -> assertThat(calculator.calculateReceive(ONE, valueOf(0.5), 15d), comparesEqualTo(valueOf(0.42))),
                () -> assertThat(calculator.calculateReceive(valueOf(25.25), valueOf(5), 10d), comparesEqualTo(valueOf(113.62)))
        );
    }

    @Test
    void testCalculateReverseSend() {
        assertAll(
                () -> assertThat(calculator.calculateReverseSend(ONE, ONE, 0d), comparesEqualTo(ONE)),
                () -> assertThat(calculator.calculateReverseSend(valueOf(100), ONE, 1d), comparesEqualTo(valueOf(101))),
                () -> assertThat(calculator.calculateReverseSend(valueOf(88.18), valueOf(0.8818), 0d), comparesEqualTo(valueOf(100)))/*,
                () -> assertThat(calculator.calculateReverseSend(valueOf(97), ONE, 3d), comparesEqualTo(valueOf(100))),
                () -> assertThat(calculator.calculateReverseSend(valueOf(0.42), valueOf(0.5), 15d), comparesEqualTo(ONE)),
                () -> assertThat(calculator.calculateReverseSend(valueOf(113.62), valueOf(5), 10d), comparesEqualTo(valueOf(25.25)))*/
        );
    }
}