package com.alexnail.currencyconversionservice.controller;

import com.alexnail.currencyconversionservice.model.Commission;
import com.alexnail.currencyconversionservice.service.CommissionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommissionController.class)
class CommissionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommissionService service;

    @Test
    public void testGetWallets() throws Exception {
        Commission usdeur = new Commission("USD", "EUR", 1.0);
        Commission eurusd = new Commission("EUR", "USD", 1.0);
        given(service.getAllCommissions()).willReturn(List.of(usdeur, eurusd));

        mvc.perform(get("/api/commissions").with(user("user").password("password"))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].currencyFrom", is("USD")))
                .andExpect(jsonPath("$[0].currencyTo", is("EUR")))
                .andExpect(jsonPath("$[0].commission", is(1.0)));
    }

    @Test
    void testGetCommission() throws Exception {
        Commission usdeur = new Commission("USD", "EUR", 1.0);
        given(service.getCommission(Pair.of("USD", "EUR"))).willReturn(usdeur);

        mvc.perform(get("/api/commissions/USD/EUR").with(user("user").password("password"))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.currencyFrom", is("USD")))
                .andExpect(jsonPath("$.currencyTo", is("EUR")))
                .andExpect(jsonPath("$.commission", is(1.0)));
    }

    @Test
    void testAddCommission() throws Exception {
        when(service.setCommission(any())).thenReturn(new Commission("USD", "EUR", 1.0));

        mvc.perform(post("/api/commissions").with(user("user").password("password"))
                .content("{\"from\": \"USD\", \"to\": \"EUR\", \"commission\": 1.0}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }
}