
package com.alexnail.currencyconversionservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ExchangeRateId implements Serializable {

    private String fromCurrency;
    private String toCurrency;
    private Long timestamp;
}
