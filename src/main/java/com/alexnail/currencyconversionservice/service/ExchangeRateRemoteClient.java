package com.alexnail.currencyconversionservice.service;

import com.alexnail.currencyconversionservice.model.ExchangeRate;
import org.springframework.stereotype.Service;

@Service
public interface ExchangeRateRemoteClient {
    ExchangeRate fetchLatestRate(String fromCurrency, String toCurrency);
}
