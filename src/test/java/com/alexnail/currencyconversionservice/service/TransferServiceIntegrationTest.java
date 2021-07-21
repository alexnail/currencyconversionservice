package com.alexnail.currencyconversionservice.service;

import com.alexnail.currencyconversionservice.config.IntegrationTestConfiguration;
import com.alexnail.currencyconversionservice.exceptions.NotEnoughMoneyException;
import com.alexnail.currencyconversionservice.model.Commission;
import com.alexnail.currencyconversionservice.model.ExchangeRate;
import com.alexnail.currencyconversionservice.model.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;

import static java.math.BigDecimal.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
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
    @Autowired
    private ExchangeRateService exchangeRateService;

    private Wallet sourceWallet;
    private Wallet targetWallet;

    @BeforeEach
    void setUp() {
        walletService.create(new Wallet(valueOf(100), "EUR", 1L));
        walletService.create(new Wallet(valueOf(150), "USD", 2L));
        sourceWallet = walletService.getById(1L);
        targetWallet = walletService.getById(2L);

        commissionService.setCommission(new Commission("EUR", "USD", 0.0));

        initRates();
    }

    @AfterEach
    void tearDown() {
        walletService.setValue(1L, valueOf(100));
        walletService.setValue(2L, valueOf(150));
    }

    @Test
    @DisplayName("Test transfer of RUB amount from EUR wallet to USD wallet.")
    public void testTransfer() {
        commissionService.setCommission(new Commission("EUR", "USD", 1.0));

        transferService.transfer(1L, 2L, valueOf(200), "RUB");
        assertAll(
                () -> assertThat(sourceWallet.getAmount(), comparesEqualTo(valueOf(75))),
                () -> assertThat(targetWallet.getAmount(), comparesEqualTo(valueOf(199.5)))
        );
    }

    @Test
    @DisplayName("Send 10EUR to USD wallet w/o commission.")
    void testSendWithoutCommission() {
        transferService.transfer(1L, 2L, valueOf(10), ZERO);
        assertAll(
                () -> assertThat(sourceWallet.getAmount(), comparesEqualTo(valueOf(90))),
                () -> assertThat(targetWallet.getAmount(), comparesEqualTo(valueOf(170)))
        );
    }

    @Test
    @DisplayName("USD wallet receives 20USD w/o commission.")
    void testReceiveWithoutCommission() {
        transferService.transfer(2L, 1L, ZERO, valueOf(20));
        assertAll(
                () -> assertThat(sourceWallet.getAmount(), comparesEqualTo(valueOf(90))),
                () -> assertThat(targetWallet.getAmount(), comparesEqualTo(valueOf(170)))
        );
    }

    @Test
    @DisplayName("Provided EUR/USD rate is 2.0 Send 10EUR to another wallet and 20USD back should result in wallet's initial amount")
    void testThatAfterDirectSendAndThenSendBack_shouldHaveInitialWalletValues_woCommission() {
        transferService.transfer(1L, 2L, valueOf(10), ZERO);
        assertAll(
                () -> assertThat(sourceWallet.getAmount(), comparesEqualTo(valueOf(90))),
                () -> assertThat(targetWallet.getAmount(), comparesEqualTo(valueOf(170)))
        );

        transferService.transfer(2L, 1L, valueOf(20), ZERO);
        assertAll(
                () -> assertThat(sourceWallet.getAmount(), comparesEqualTo(valueOf(100))),
                () -> assertThat(targetWallet.getAmount(), comparesEqualTo(valueOf(150)))
        );
    }

    @Test
    @DisplayName("Provided EUR/USD rate is 2.0 send 10EUR to another wallet and then request it back should result in wallet's initial amount")
    void testThatAfterDirectSendAndThenRequestInOppositeDirection_shouldHaveInitialWalletValues_woCommission() {
        transferService.transfer(1L, 2L, valueOf(10), ZERO);
        assertAll(
                () -> assertThat(sourceWallet.getAmount(), comparesEqualTo(valueOf(90))),
                () -> assertThat(targetWallet.getAmount(), comparesEqualTo(valueOf(170)))
        );

        transferService.transfer(1L, 2L, ZERO, valueOf(10));
        assertAll(
                () -> assertThat(sourceWallet.getAmount(), comparesEqualTo(valueOf(100))),
                () -> assertThat(targetWallet.getAmount(), comparesEqualTo(valueOf(150)))
        );
    }

    @Test
    @DisplayName("Test transfer bigger amount than is available on the source wallet.")
    public void testTransferMoreThanAvailable() {
        assertThrows(NotEnoughMoneyException.class,
                () -> transferService.transfer(1L, 2L, valueOf(1000.0), "RUB")
        );
    }

    private void initRates() {
        exchangeRateService.setRate("EUR", "USD",
                new ExchangeRate("EUR", "USD", valueOf(2), Timestamp.from(Instant.now())));
        exchangeRateService.setRate("USD", "EUR",
                new ExchangeRate("USD", "EUR", valueOf(0.5), Timestamp.from(Instant.now())));
        exchangeRateService.setRate("EUR", "RUB",
                new ExchangeRate("EUR", "RUB", valueOf(8), Timestamp.from(Instant.now())));
        exchangeRateService.setRate("RUB", "EUR",
                new ExchangeRate("RUB", "EUR", valueOf(0.125), Timestamp.from(Instant.now())));
        exchangeRateService.setRate("USD", "RUB",
                new ExchangeRate("USD", "RUB", valueOf(4), Timestamp.from(Instant.now())));
        exchangeRateService.setRate("RUB", "USD",
                new ExchangeRate("RUB", "USD", valueOf(0.25), Timestamp.from(Instant.now())));
    }
}