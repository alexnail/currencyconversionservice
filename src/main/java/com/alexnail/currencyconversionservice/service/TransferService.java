package com.alexnail.currencyconversionservice.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface TransferService {
    void transfer(BigDecimal amount, String currency, Long sourceWalletId, Long targetWalletId);
}
