package com.alexnail.currencyconversionservice.dto;

import lombok.Data;

@Data
public class CommissionDto {
    private String currencyFrom;
    private String currencyTo;
    private Double commission;
}
