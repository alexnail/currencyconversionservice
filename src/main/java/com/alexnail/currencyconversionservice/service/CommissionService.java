package com.alexnail.currencyconversionservice.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface CommissionService {

    void setCommission(Double commission, Pair<String, String> currencyPair);

    Double getCommission(Pair<String, String> currencyPair);

    BigDecimal getAmountMinusCommission(BigDecimal amount, Pair<String, String> currencyPair);
}
