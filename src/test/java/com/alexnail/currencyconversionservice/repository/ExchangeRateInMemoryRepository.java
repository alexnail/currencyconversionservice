package com.alexnail.currencyconversionservice.repository;

import org.springframework.data.util.Pair;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRateInMemoryRepository implements ExchangeRateRepository {

    private static final Map<Pair<String, String>, Double> rates = new HashMap<>();

    @PostConstruct
    private void init() {
        rates.put(Pair.of("USD", "EUR"), 0.84);
        rates.put(Pair.of("EUR", "USD"), 1.19);
        rates.put(Pair.of("EUR", "RUB"), 88.25);
        rates.put(Pair.of("RUB", "EUR"), 0.011);
        rates.put(Pair.of("USD", "RUB"), 74.32);
        rates.put(Pair.of("RUB", "USD"), 0.013);
    }

    @Override
    public Double getRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency))
            return 1.0;
        return rates.get(Pair.of(fromCurrency, toCurrency));
    }
}
