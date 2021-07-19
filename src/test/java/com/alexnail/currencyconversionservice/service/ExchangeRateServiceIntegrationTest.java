package com.alexnail.currencyconversionservice.service;

import com.alexnail.currencyconversionservice.config.IntegrationTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@Import(IntegrationTestConfiguration.class)
@TestPropertySource(locations = {"classpath:integration-test.properties"})
class ExchangeRateServiceIntegrationTest {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Test
    void testExchange() {
        BigDecimal exchange = exchangeRateService.exchange(BigDecimal.ONE, "EUR", "EUR");

        assertNotNull(exchange);
        assertEquals(BigDecimal.ONE, exchange);
    }
}