package com.alexnail.currencyconversionservice.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository {
    Double getRate(String fromCurrency, String toCurrency);
}
