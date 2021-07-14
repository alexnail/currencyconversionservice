package com.alexnail.currencyconversionservice.service;

import com.alexnail.currencyconversionservice.model.Wallet;
import com.alexnail.currencyconversionservice.repository.CommissionInMemoryRepository;
import com.alexnail.currencyconversionservice.repository.CommissionRepository;
import com.alexnail.currencyconversionservice.repository.ExchangeRateInMemoryRepository;
import com.alexnail.currencyconversionservice.repository.ExchangeRateRepository;
import com.alexnail.currencyconversionservice.repository.impl.WalletInMemoryRepository;
import com.alexnail.currencyconversionservice.repository.WalletRepository;
import com.alexnail.currencyconversionservice.service.impl.CommissionServiceImpl;
import com.alexnail.currencyconversionservice.service.impl.ExchangeRateServiceImpl;
import com.alexnail.currencyconversionservice.service.impl.TransferServiceImpl;
import com.alexnail.currencyconversionservice.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class TransferServiceTest {

    @Autowired
    private TransferService transferService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private CommissionService commissionService;

    @BeforeEach
    private void init() {
        walletService.create(Wallet.builder()
                .value(BigDecimal.valueOf(100))
                .currency("EUR").build());
        walletService.create(Wallet.builder()
                .value(BigDecimal.valueOf(150))
                .currency("USD").build());

        commissionService.setCommission(1.0, Pair.of("EUR", "USD"));
    }

    @Test
    @DisplayName("Test transfer of RUB amount from EUR wallet to USD wallet.")
    public void testTransfer() {
        transferService.transfer(BigDecimal.valueOf(1000.0), "RUB", 1L, 2L);

        Wallet sourceWallet = walletService.get(1L);
        Wallet targetWallet = walletService.get(2L);
        assertAll(
                () -> assertEquals(BigDecimal.valueOf(89.0).stripTrailingZeros(), sourceWallet.getValue().stripTrailingZeros()),
                () -> assertEquals(BigDecimal.valueOf(162.9591).stripTrailingZeros(), targetWallet.getValue().stripTrailingZeros())
        );
    }

    @TestConfiguration
    static class TransferServiceTestConfiguration {

        @Bean
        WalletRepository walletRepository() {
            return new WalletInMemoryRepository();
        }

        @Bean
        WalletService walletService() {
            return new WalletServiceImpl(walletRepository());
        }

        @Bean
        ExchangeRateRepository exchangeRepository() {
            return new ExchangeRateInMemoryRepository();
        }

        @Bean
        ExchangeRateService exchangeRateService() {
            return new ExchangeRateServiceImpl(exchangeRepository());
        }

        @Bean
        CommissionRepository commissionRepository() {
            return new CommissionInMemoryRepository();
        }

        @Bean
        CommissionService commissionService() {
            return new CommissionServiceImpl(commissionRepository());
        }

        @Bean
        TransferService transferService() {
            return new TransferServiceImpl(walletService(), exchangeRateService(), commissionService());
        }
    }

}