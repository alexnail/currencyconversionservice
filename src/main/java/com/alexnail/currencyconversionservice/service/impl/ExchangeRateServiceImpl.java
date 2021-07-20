package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.model.ExchangeRate;
import com.alexnail.currencyconversionservice.repository.ExchangeRateRepository;
import com.alexnail.currencyconversionservice.service.ExchangeRateRemoteClient;
import com.alexnail.currencyconversionservice.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
        if (fromCurrency.equals(toCurrency))
            return new ExchangeRate(fromCurrency, toCurrency, BigDecimal.ONE, Timestamp.from(Instant.now()));
        ExchangeRate latestRate = repository.findByLatestTimestamp(fromCurrency, toCurrency);
        if (latestRate == null || isObsoleteRate(latestRate)) {
            latestRate = exchangeRateRemoteClient.fetchLatestRate(fromCurrency, toCurrency);
            Objects.requireNonNull(latestRate, String.format("Failed to fetch rate for [%s/%s] pair", fromCurrency, toCurrency));
            repository.saveAndFlush(latestRate);
        }
        return latestRate;
    }

    private boolean isObsoleteRate(ExchangeRate latestRate) {
        Instant expiredInstant = latestRate.getTimestamp().toInstant().plus(obsoleteThresholdMinutes, ChronoUnit.MINUTES);
        return Timestamp.from(Instant.now()).after(Timestamp.from(expiredInstant));
    }
}
