package com.alexnail.currencyconversionservice.controller;

import com.alexnail.currencyconversionservice.model.ExchangeRate;
import com.alexnail.currencyconversionservice.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExchangeRateController.class)
class ExchangeRateControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExchangeRateService service;

    @Test
    public void testGetWallets() throws Exception {
        ExchangeRate exchangeRate =  new ExchangeRate("USD", "EUR",
                BigDecimal.valueOf(0.849425), Timestamp.from(Instant.now()));
        given(service.getRate("USD", "EUR")).willReturn(exchangeRate);
        mvc.perform(get("/api/exchangerates/USD/EUR").with(user("user").password("password"))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.from", is("USD")))
                .andExpect(jsonPath("$.to", is("EUR")))
                .andExpect(jsonPath("$.rate", is(0.849425)));
    }
}