package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.model.Commission;
import com.alexnail.currencyconversionservice.model.CommissionId;
import com.alexnail.currencyconversionservice.repository.CommissionRepository;
import com.alexnail.currencyconversionservice.service.CommissionService;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

@Service
@AllArgsConstructor
public class CommissionServiceImpl implements CommissionService {

    private final CommissionRepository repository;

    @Override
    public Collection<Commission> getAllCommissions() {
        return repository.findAll();
    }

    @Override
    public Commission setCommission(Commission commission) {
        return repository.save(commission);
    }

    @Override
    public Commission getCommission(Pair<String, String> pair) {
        return repository.findById(new CommissionId(pair.getFirst(), pair.getSecond())).orElseThrow();
    }

    @Override
    public BigDecimal getAmountMinusCommission(BigDecimal amount, Pair<String, String> currencyPair) {
        Commission commission = getCommission(currencyPair);
        return amount.subtract(amount.multiply(BigDecimal.valueOf(commission.getCommission()/100)));
    }

    @Override
    public void delete(Commission commission) {
        repository.delete(commission);
    }
}
