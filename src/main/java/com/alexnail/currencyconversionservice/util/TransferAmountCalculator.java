package com.alexnail.currencyconversionservice.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TransferAmountCalculator {

    public BigDecimal calculate(BigDecimal send, BigDecimal receive, BigDecimal exchangeRate, Double commission) {
        if (!isEmptyValue(send) && isEmptyValue(receive)) {
            return calculateReceive(send, exchangeRate, commission).stripTrailingZeros();
        } else if (isEmptyValue(send) && !isEmptyValue(receive)) {
            return calculateReverseSend(receive, exchangeRate, commission).stripTrailingZeros();
        } else if (isEmptyValue(send) && isEmptyValue(receive)) {
            return send;
        } else {
            if (sendAndReverseSendAreNotEqual(send, receive, exchangeRate, commission))
                throw new RuntimeException("Both send and receive have non-empty values. Can't decide which value to use for amount calculation.");
            else
                return calculateReceive(send, exchangeRate, commission);
        }
    }

    private BigDecimal calculateReceive(BigDecimal send, BigDecimal exchangeRate, Double commission) {
        BigDecimal exchange = send.multiply(exchangeRate);
        if (commission.compareTo(0.0) == 0)
            return exchange;
        BigDecimal commissionAmount = exchange.multiply(BigDecimal.valueOf(commission/100));
        return exchange.subtract(commissionAmount);
    }

    private BigDecimal calculateReverseSend(BigDecimal receive, BigDecimal exchangeRate, Double commission) {
        BigDecimal exchange = receive.divide(exchangeRate, RoundingMode.HALF_UP);
        if (commission.compareTo(0.0) == 0)
            return exchange;
        BigDecimal commissionAmount = exchange.multiply(BigDecimal.valueOf(commission/100));
        return exchange.add(commissionAmount);
    }

    private boolean sendAndReverseSendAreNotEqual(BigDecimal send, BigDecimal receive, BigDecimal exchangeRate, Double commission) {
        return send.stripTrailingZeros().compareTo(calculateReverseSend(receive, exchangeRate, commission).stripTrailingZeros()) != 0;
    }

    private boolean isEmptyValue(BigDecimal value) {
        return value == null || BigDecimal.ZERO.compareTo(value) == 0;
    }
}
