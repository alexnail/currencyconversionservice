package com.alexnail.currencyconversionservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDto {
    private Long id;
    private BigDecimal amount;
    private String currency;
}
