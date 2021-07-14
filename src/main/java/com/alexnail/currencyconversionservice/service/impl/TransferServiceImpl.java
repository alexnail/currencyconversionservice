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
        Wallet sourceWallet = walletService.get(sourceWalletId);
        Wallet targetWallet = walletService.get(targetWalletId);

        BigDecimal amountFrom = exchangeRateService.exchange(amount, currency, sourceWallet.getCurrency());
        BigDecimal amountTo = exchangeRateService.exchange(amountFrom, sourceWallet.getCurrency(), targetWallet.getCurrency());
        sourceWallet.setValue(sourceWallet.getValue().subtract(amountFrom));

        BigDecimal amountToExCommission = commissionService.getAmountMinusCommission(
                amountTo, Pair.of(sourceWallet.getCurrency(), targetWallet.getCurrency())
        );
        targetWallet.setValue(targetWallet.getValue().add(amountToExCommission));
    }
}
