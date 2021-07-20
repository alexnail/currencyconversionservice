package com.alexnail.currencyconversionservice.service;

import java.math.BigDecimal;

public interface TransferService {
    void transfer(Long sourceWalletId, Long targetWalletId, BigDecimal amount, String currency);

    void transfer(Long sourceWalletId, Long targetWalletId, BigDecimal send, BigDecimal receive);
}
