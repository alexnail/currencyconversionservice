package com.alexnail.currencyconversionservice.config;

import com.alexnail.currencyconversionservice.repository.CommissionInMemoryRepository;
import com.alexnail.currencyconversionservice.repository.CommissionRepository;
import com.alexnail.currencyconversionservice.repository.ExchangeRateInMemoryRepository;
import com.alexnail.currencyconversionservice.repository.ExchangeRateRepository;
import com.alexnail.currencyconversionservice.repository.WalletRepository;
import com.alexnail.currencyconversionservice.repository.impl.WalletInMemoryRepository;
import com.alexnail.currencyconversionservice.service.CommissionService;
import com.alexnail.currencyconversionservice.service.ExchangeRateService;
import com.alexnail.currencyconversionservice.service.TransferService;
import com.alexnail.currencyconversionservice.service.WalletService;
import com.alexnail.currencyconversionservice.service.impl.CommissionServiceImpl;
import com.alexnail.currencyconversionservice.service.impl.ExchangeRateServiceImpl;
import com.alexnail.currencyconversionservice.service.impl.TransferServiceImpl;
import com.alexnail.currencyconversionservice.service.impl.WalletServiceImpl;
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
