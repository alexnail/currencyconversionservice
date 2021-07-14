package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.repository.CommissionRepository;
import com.alexnail.currencyconversionservice.service.CommissionService;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;

@AllArgsConstructor
public class CommissionServiceImpl implements CommissionService {

    CommissionRepository repository;

    @Override
    public void setCommission(Double commission, Pair<String, String> currencyPair) {
        repository.save(commission, currencyPair);
    }

    @Override
    public Double getCommission(Pair<String, String> currencyPair) {
        return repository.find(currencyPair);
    }

    @Override
    public BigDecimal getAmountMinusCommission(BigDecimal amount, Pair<String, String> currencyPair) {
        Double commission = getCommission(currencyPair);
        return amount.subtract(amount.multiply(BigDecimal.valueOf(commission/100)));
    }
}
