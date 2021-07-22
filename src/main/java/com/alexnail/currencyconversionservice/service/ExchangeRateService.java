package com.alexnail.currencyconversionservice.service;

import com.alexnail.currencyconversionservice.model.ExchangeRate;

import java.math.BigDecimal;

public interface ExchangeRateService {
    BigDecimal exchange(BigDecimal amount, String fromCurrency, String toCurrency);

    ExchangeRate getRate(String from, String to);

    ExchangeRate setRate(String fromCurrency, String toCurrency, ExchangeRate exchangeRate);

    void deleteRate(String from, String to);
}
