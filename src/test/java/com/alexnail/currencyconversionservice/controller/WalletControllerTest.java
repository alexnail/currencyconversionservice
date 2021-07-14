package com.alexnail.currencyconversionservice.controller;

import com.alexnail.currencyconversionservice.model.Wallet;
import com.alexnail.currencyconversionservice.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WalletService service;

    @Test
    public void testGetWallets() throws Exception {
        Wallet wallet = Wallet.builder().id(1L).amount(BigDecimal.ONE).currency("EUR").build();
        given(service.findAll()).willReturn(Collections.singletonList(wallet));
        mvc.perform(get("/api/wallets")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].amount", is(1)))
                .andExpect(jsonPath("$[0].currency", is(wallet.getCurrency())));
    }
}