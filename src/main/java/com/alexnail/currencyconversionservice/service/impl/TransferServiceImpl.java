package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.model.Commission;
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
import java.math.RoundingMode;

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

        if (hasNonEmptyValue(send) && !hasNonEmptyValue(receive)) {
            transfer(sourceWalletId, targetWalletId, send, sourceCurrency);
        } else {
            if (!hasNonEmptyValue(send) && hasNonEmptyValue(receive)) {
                transfer(sourceWalletId, targetWalletId,
                        calculator.calculate(send, receive,
                                exchangeRateService.getRate(sourceCurrency, targetCurrency).getRate(),
                                commissionService.getCommission(sourceCurrency, targetCurrency).getCommission()),
                        sourceCurrency);
            } else { //both send and receive are provided
                if (sendAndReceiveAreTheSame(send, receive, sourceCurrency, targetCurrency))
                    transfer(sourceWalletId, targetWalletId, send, sourceCurrency);
                else
                    throw new RuntimeException("Both send and receive have non-empty values. Can't decide which value to use for transfer amount calculation.");
            }
        }
    }

    private boolean hasNonEmptyValue(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) > 0;
    }

    private BigDecimal calculateSendFromReceive(BigDecimal receive, String sourceCurrency, String targetCurrency) {
        BigDecimal rate = exchangeRateService.getRate(sourceCurrency, targetCurrency).getRate();
        Commission commission = commissionService.getCommission(sourceCurrency, targetCurrency);
        if (commission.getCommission().compareTo(0.0) == 0)
            return receive.divide(rate, RoundingMode.HALF_UP);
        else
            return receive.divide(rate, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(commission.getCommission()), RoundingMode.HALF_UP);
    }

    private boolean sendAndReceiveAreTheSame(BigDecimal send, BigDecimal receive, String sourceCurrency, String targetCurrency) {
        return send.compareTo(calculateSendFromReceive(receive, sourceCurrency, targetCurrency)) == 0;
    }
}
