package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.model.Wallet;
import com.alexnail.currencyconversionservice.service.CommissionService;
import com.alexnail.currencyconversionservice.service.ExchangeRateService;
import com.alexnail.currencyconversionservice.service.TransferService;
import com.alexnail.currencyconversionservice.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@AllArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final WalletService walletService;
    private final ExchangeRateService exchangeRateService;
    private final CommissionService commissionService;

    @Transactional
    public void transfer(BigDecimal amount, String currency,
                         Long sourceWalletId, Long targetWalletId) {
        Wallet sourceWallet = walletService.getById(sourceWalletId);
        Wallet targetWallet = walletService.getById(targetWalletId);

        BigDecimal withdrawAmount = exchangeRateService.exchange(amount, currency, sourceWallet.getCurrency());
        BigDecimal depositAmount = exchangeRateService.exchange(withdrawAmount, sourceWallet.getCurrency(), targetWallet.getCurrency());

        BigDecimal depositExCommission = commissionService.getAmountMinusCommission(
                depositAmount, Pair.of(sourceWallet.getCurrency(), targetWallet.getCurrency())
        );
        walletService.withdraw(sourceWalletId, withdrawAmount);
        walletService.deposit(targetWalletId, depositExCommission);
    }
}
