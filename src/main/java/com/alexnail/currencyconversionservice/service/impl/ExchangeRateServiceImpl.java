package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.repository.ExchangeRateRepository;
import com.alexnail.currencyconversionservice.service.ExchangeRateService;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository repository;

    public BigDecimal exchange(BigDecimal amount, String fromCurrency, String toCurrency) {
        Double rate = repository.getRate(fromCurrency, toCurrency);
        return amount.multiply(BigDecimal.valueOf(rate));
    }
}
