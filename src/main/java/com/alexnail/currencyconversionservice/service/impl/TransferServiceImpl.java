package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.model.Wallet;
import com.alexnail.currencyconversionservice.service.CommissionService;
import com.alexnail.currencyconversionservice.service.ExchangeRateService;
import com.alexnail.currencyconversionservice.service.TransferService;
import com.alexnail.currencyconversionservice.service.WalletService;
import com.alexnail.currencyconversionservice.util.TransferAmountCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {

    private static final TransferAmountCalculator calculator = new TransferAmountCalculator();

    private final WalletService walletService;
    private final ExchangeRateService exchangeRateService;
    private final CommissionService commissionService;


    @Transactional
    public void transfer(Long sourceWalletId, Long targetWalletId, BigDecimal amount, String currency) {
        Wallet sourceWallet = walletService.getById(sourceWalletId);
        Wallet targetWallet = walletService.getById(targetWalletId);

        BigDecimal withdrawAmount = exchangeRateService.exchange(amount, currency, sourceWallet.getCurrency());
        BigDecimal depositAmount = exchangeRateService.exchange(withdrawAmount, sourceWallet.getCurrency(), targetWallet.getCurrency());

        BigDecimal depositExCommission = commissionService.getAmountMinusCommission(
                depositAmount, sourceWallet.getCurrency(), targetWallet.getCurrency()
        );
        walletService.withdraw(sourceWalletId, withdrawAmount);
        walletService.deposit(targetWalletId, depositExCommission);
    }

    @Override
    public void transfer(Long sourceWalletId, Long targetWalletId, BigDecimal send, BigDecimal receive) {
        String sourceCurrency = walletService.getById(sourceWalletId).getCurrency();
        String targetCurrency = walletService.getById(targetWalletId).getCurrency();
        BigDecimal rate = exchangeRateService.getRate(sourceCurrency, targetCurrency).getRate();
        Double commission = commissionService.getCommission(sourceCurrency, targetCurrency).getCommission();

        if (!isEmptyValue(send) && isEmptyValue(receive)) {
            transfer(sourceWalletId, targetWalletId, send, sourceCurrency);
        } else if (isEmptyValue(send) && !isEmptyValue(receive)) {
            transfer(sourceWalletId, targetWalletId,
                    calculator.calculateReverseSend(send, rate, commission), sourceCurrency);
        } else { //both send and receive values are provided
            if (sendAndReverseSendAreEqual(send, receive, rate, commission))
                transfer(sourceWalletId, targetWalletId, send, sourceCurrency);
            else
                throw new RuntimeException("Both send and receive have non-empty values. Can't decide which value to use for transfer amount calculation.");
        }
    }

    private boolean sendAndReverseSendAreEqual(BigDecimal send, BigDecimal receive, BigDecimal rate, Double commission) {
        return send.stripTrailingZeros()
                .compareTo(calculator.calculateReverseSend(receive, rate, commission).stripTrailingZeros()) == 0;
    }

    private boolean isEmptyValue(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) == 0;
    }
}
