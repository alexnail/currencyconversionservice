package com.alexnail.currencyconversionservice.service;

import com.alexnail.currencyconversionservice.model.Commission;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.Collection;

public interface CommissionService {

    Collection<Commission> getAllCommissions();

    Commission setCommission(Commission commission);

    Commission getCommission(Pair<String, String> currencyPair);

    BigDecimal getAmountMinusCommission(BigDecimal amount, Pair<String, String> currencyPair);

    void delete(Commission commission);
}
