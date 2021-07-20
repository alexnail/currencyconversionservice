package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.model.Commission;
import com.alexnail.currencyconversionservice.model.CommissionId;
import com.alexnail.currencyconversionservice.repository.CommissionRepository;
import com.alexnail.currencyconversionservice.service.CommissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

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
    public Commission getCommission(String currencyFrom, String currencyTo) {
        Optional<Commission> optional = repository.findById(new CommissionId(currencyFrom, currencyTo));
        return optional.orElse(new Commission(currencyFrom, currencyTo, 0.0));
    }

    @Override
    public BigDecimal getAmountMinusCommission(BigDecimal amount, String currencyFrom, String currencyTo) {
        Commission commission = getCommission(currencyFrom, currencyTo);
        return amount.subtract(amount.multiply(BigDecimal.valueOf(commission.getCommission()/100)));
    }

    @Override
    public void delete(Commission commission) {
        repository.delete(commission);
    }
}
