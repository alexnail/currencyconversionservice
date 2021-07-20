package com.alexnail.currencyconversionservice.service;

import com.alexnail.currencyconversionservice.model.Commission;

import java.math.BigDecimal;
import java.util.Collection;

public interface CommissionService {

    Collection<Commission> getAllCommissions();

    Commission setCommission(Commission commission);

    Commission getCommission(String currencyFrom, String currencyTo);

    BigDecimal getAmountMinusCommission(BigDecimal amount, String currencyFrom, String currencyTo);

    void delete(Commission commission);
}
