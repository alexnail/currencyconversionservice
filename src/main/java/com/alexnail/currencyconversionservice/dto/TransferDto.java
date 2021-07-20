package com.alexnail.currencyconversionservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {
    private Long sourceWalletId;
    private Long targetWalletId;
    private BigDecimal send;
    private BigDecimal receive;
}
