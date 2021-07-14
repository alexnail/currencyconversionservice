package com.alexnail.currencyconversionservice.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ExchangeRateService {
    BigDecimal exchange(BigDecimal amount, String fromCurrency, String toCurrency);
}
