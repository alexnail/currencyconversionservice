package com.alexnail.currencyconversionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommissionId implements Serializable {
    private String currencyFrom;
    private String currencyTo;
}
