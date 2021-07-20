package com.alexnail.currencyconversionservice.service;

import com.alexnail.currencyconversionservice.config.IntegrationTestConfiguration;
import com.alexnail.currencyconversionservice.model.Commission;
import com.alexnail.currencyconversionservice.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(IntegrationTestConfiguration.class)
@TestPropertySource(locations = {"classpath:integration-test.properties"})
public class TransferServiceIntegrationTest {

    @Autowired
    private TransferService transferService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private CommissionService commissionService;

    @BeforeEach
    private void init() {
        walletService.create(new Wallet(BigDecimal.valueOf(100), "EUR", 1L));
        walletService.create(new Wallet(BigDecimal.valueOf(150), "USD", 2L));

        commissionService.setCommission(new Commission("EUR", "USD", 1.0));
    }

    @Test
    @DisplayName("Test transfer of RUB amount from EUR wallet to USD wallet.")
    public void testTransfer() {
        transferService.transfer(1L, 2L, BigDecimal.valueOf(1000.0), "RUB");

        Wallet sourceWallet = walletService.getById(1L);
        Wallet targetWallet = walletService.getById(2L);
        assertAll(
                () -> assertEquals(BigDecimal.valueOf(89.0).stripTrailingZeros(), sourceWallet.getAmount().stripTrailingZeros()),
                () -> assertEquals(BigDecimal.valueOf(162.9591).stripTrailingZeros(), targetWallet.getAmount().stripTrailingZeros())
        );
    }
}