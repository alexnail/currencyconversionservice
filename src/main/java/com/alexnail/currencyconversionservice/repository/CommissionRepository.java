package com.alexnail.currencyconversionservice.repository;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRepository {
    void save(Double commission, Pair<String, String> currencyPair);

    Double find(Pair<String, String> currencyPair);
}
