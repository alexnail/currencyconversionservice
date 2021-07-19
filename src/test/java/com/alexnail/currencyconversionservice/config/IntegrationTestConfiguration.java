package com.alexnail.currencyconversionservice.config;

import com.alexnail.currencyconversionservice.repository.*;
import com.alexnail.currencyconversionservice.repository.impl.WalletInMemoryRepository;
import com.alexnail.currencyconversionservice.service.*;
import com.alexnail.currencyconversionservice.service.impl.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class IntegrationTestConfiguration {

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
    ExchangeRateRemoteClient exchangeRateRemoteService() {
        return new CurrencyLayerClient();
    }

    @Bean
    ExchangeRateService exchangeRateService() {
        return new ExchangeRateServiceImpl(exchangeRepository(), exchangeRateRemoteService());
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
