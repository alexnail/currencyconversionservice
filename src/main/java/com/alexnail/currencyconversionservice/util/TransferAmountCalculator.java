package com.alexnail.currencyconversionservice.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TransferAmountCalculator {

    public BigDecimal calculateReceive(BigDecimal send, BigDecimal exchangeRate, Double commission) {
        BigDecimal exchange = send.multiply(exchangeRate);
        if (isZeroCommission(commission))
            return exchange;
        BigDecimal commissionAmount = getCommissionAmount(exchange, commission);
        return exchange.subtract(commissionAmount);
    }

    public BigDecimal calculateReverseSend(BigDecimal receive, BigDecimal exchangeRate, Double commission) {
        BigDecimal exchange = receive.divide(exchangeRate, RoundingMode.HALF_UP);
        if (isZeroCommission(commission))
            return exchange;
        /*
        * Following is the wrong logic, but I can't come up with anything better at the moment.
        * The proper one must be getCommissionAmount(<amount this method would return if we could properly calculate commission amount>, commission)
        * It's sort of neverending loop.
        */
        BigDecimal commissionAmount = getCommissionAmount(exchange, commission);
        return exchange.add(commissionAmount);
    }

    private BigDecimal getCommissionAmount(BigDecimal value, Double commission) {
        BigDecimal commissionAmount = value
                .multiply(percentToRate(commission));
        return commissionAmount
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal percentToRate(Double commission) {
        return BigDecimal.valueOf(commission / 100);
    }

    private boolean isZeroCommission(Double commission) {
        return commission.compareTo(0.0) == 0;
    }
}
