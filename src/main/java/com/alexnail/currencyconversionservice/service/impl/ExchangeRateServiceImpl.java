package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.model.ExchangeRate;
import com.alexnail.currencyconversionservice.repository.ExchangeRateRepository;
import com.alexnail.currencyconversionservice.service.ExchangeRateRemoteClient;
import com.alexnail.currencyconversionservice.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Value("${exchangerate.obsolete.threshold.mins}")
    private Integer obsoleteThresholdMinutes;

    private final ExchangeRateRepository repository;

    private final ExchangeRateRemoteClient exchangeRateRemoteClient;

    @Override
    public BigDecimal exchange(BigDecimal amount, String fromCurrency, String toCurrency) {
        ExchangeRate rate = getRate(fromCurrency, toCurrency);
        return amount.multiply(rate.getRate());
    }

    @Override
    public ExchangeRate getRate(String fromCurrency, String toCurrency) {
        ExchangeRate latestRate = repository.findByLatestTimestamp(fromCurrency, toCurrency);
        if (latestRate == null || isObsoleteRate(latestRate)) {
            latestRate = exchangeRateRemoteClient.fetchLatestRate(fromCurrency, toCurrency);
            Objects.requireNonNull(latestRate, String.format("Failed to fetch rate for [%s/%s] pair", fromCurrency, toCurrency));
            repository.saveAndFlush(latestRate);
        }
        return latestRate;
    }

    private boolean isObsoleteRate(ExchangeRate latestRate) {
        return System.currentTimeMillis() > latestRate.getTimestamp() + obsoleteThresholdMinutes * 60 * 1000;
    }
}
