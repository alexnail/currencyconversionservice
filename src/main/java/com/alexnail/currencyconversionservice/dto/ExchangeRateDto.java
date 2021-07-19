package com.alexnail.currencyconversionservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRateDto {
    private String from;
    private String to;
    private BigDecimal rate;
    private Long timestamp;
}
