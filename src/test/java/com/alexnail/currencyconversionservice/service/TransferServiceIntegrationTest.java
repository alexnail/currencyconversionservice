package com.alexnail.currencyconversionservice.service;

import com.alexnail.currencyconversionservice.config.IntegrationTestConfiguration;
import com.alexnail.currencyconversionservice.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.util.Pair;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        walletService.create(Wallet.builder()
                .amount(BigDecimal.valueOf(100))
                .currency("EUR").build());
        walletService.create(Wallet.builder()
                .amount(BigDecimal.valueOf(150))
                .currency("USD").build());

        commissionService.setCommission(1.0, Pair.of("EUR", "USD"));
    }

    @Test
    @DisplayName("Test transfer of RUB amount from EUR wallet to USD wallet.")
    public void testTransfer() {
        transferService.transfer(BigDecimal.valueOf(1000.0), "RUB", 1L, 2L);

        Wallet sourceWallet = walletService.getById(1L);
        Wallet targetWallet = walletService.getById(2L);
        assertAll(
                () -> assertEquals(BigDecimal.valueOf(89.0).stripTrailingZeros(), sourceWallet.getAmount().stripTrailingZeros()),
                () -> assertEquals(BigDecimal.valueOf(162.9591).stripTrailingZeros(), targetWallet.getAmount().stripTrailingZeros())
        );
    }
}