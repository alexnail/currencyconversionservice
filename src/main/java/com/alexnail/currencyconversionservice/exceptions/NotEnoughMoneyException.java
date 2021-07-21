package com.alexnail.currencyconversionservice.exceptions;

import java.math.BigDecimal;

public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(BigDecimal withdrawAmount, BigDecimal amount, String currency) {
        super(String.format("Attempt has been made to withdraw %.2f %s, but only %.2f %s is available.",
                withdrawAmount.doubleValue(), currency, amount.doubleValue(), currency));
    }
}
