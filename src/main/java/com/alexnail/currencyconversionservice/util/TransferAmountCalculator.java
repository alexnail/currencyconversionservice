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
        BigDecimal commissionAmount = getCommissionAmount(exchange, commission);
        return exchange.add(commissionAmount);
    }

    private BigDecimal getCommissionAmount(BigDecimal value, Double commission) {
        BigDecimal commissionAmount = value
                .multiply(percentToRate(commission));
        BigDecimal scaled = commissionAmount
                .setScale(value.scale(), RoundingMode.HALF_UP);
        return scaled;
    }

    private BigDecimal percentToRate(Double commission) {
        return BigDecimal.valueOf(commission / 100);
    }

    private boolean isZeroCommission(Double commission) {
        return commission.compareTo(0.0) == 0;
    }
}
