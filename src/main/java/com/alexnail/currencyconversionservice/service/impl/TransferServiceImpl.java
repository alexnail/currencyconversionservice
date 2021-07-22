package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.exceptions.AmbiguousTransferValuesException;
import com.alexnail.currencyconversionservice.exceptions.NotEnoughMoneyException;
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


    public void transfer(Long sourceWalletId, Long targetWalletId, BigDecimal amount, String currency) {
        Wallet sourceWallet = walletService.getById(sourceWalletId);
        Wallet targetWallet = walletService.getById(targetWalletId);

        BigDecimal withdrawAmount = exchangeRateService.exchange(amount, currency, sourceWallet.getCurrency());
        if (isNotEnoughMoney(withdrawAmount, sourceWallet))
            throw new NotEnoughMoneyException(withdrawAmount, sourceWallet.getAmount(), sourceWallet.getCurrency());

        BigDecimal depositAmount = exchangeRateService.exchange(withdrawAmount, sourceWallet.getCurrency(), targetWallet.getCurrency());
        BigDecimal depositExCommission = commissionService.getAmountMinusCommission(
                depositAmount, sourceWallet.getCurrency(), targetWallet.getCurrency()
        );
        walletService.withdraw(sourceWalletId, withdrawAmount);
        walletService.deposit(targetWalletId, depositExCommission);
    }

    private boolean isNotEnoughMoney(BigDecimal withdrawAmount, Wallet sourceWallet) {
        return withdrawAmount.stripTrailingZeros().compareTo(sourceWallet.getAmount().stripTrailingZeros()) > 0;
    }

    @Override
    @Transactional
    public void transfer(Long sourceWalletId, Long targetWalletId, BigDecimal send, BigDecimal receive) {
        String sourceCurrency = walletService.getById(sourceWalletId).getCurrency();
        String targetCurrency = walletService.getById(targetWalletId).getCurrency();
        Double commission = commissionService.getCommission(sourceCurrency, targetCurrency).getCommission();

        if (!isEmptyValue(send) && isEmptyValue(receive)) {
            transfer(sourceWalletId, targetWalletId, send, sourceCurrency);
        } else if (isEmptyValue(send) && !isEmptyValue(receive)) {
            BigDecimal reverseRate = exchangeRateService.getRate(targetCurrency, sourceCurrency).getRate();
            BigDecimal reverseSend = calculator.calculateReverseSend(receive, reverseRate, commission);
            transfer(targetWalletId, sourceWalletId, reverseSend, targetCurrency);
        } else {
            throw new AmbiguousTransferValuesException();
        }
    }

    private boolean isEmptyValue(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) == 0;
    }
}
