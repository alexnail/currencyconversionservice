package com.alexnail.currencyconversionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@IdClass(ExchangeRateId.class)
public class ExchangeRate {

    @Id
    private String fromCurrency;
    @Id
    private String toCurrency;
    private BigDecimal rate;
    @Id
    private Long timestamp;
}
