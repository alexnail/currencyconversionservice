package com.alexnail.currencyconversionservice.repository;

import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class CommissionInMemoryRepository implements CommissionRepository {

    private final Map<Pair<String, String>, Double> commissions = new HashMap<>();

    @Override
    public void save(Double commission, Pair<String, String> currencyPair) {
        commissions.put(currencyPair, commission);
    }

    @Override
    public Double find(Pair<String, String> currencyPair) {
        return commissions.getOrDefault(currencyPair, 0.0);
    }
}
