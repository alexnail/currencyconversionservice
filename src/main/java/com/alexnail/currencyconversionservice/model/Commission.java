package com.alexnail.currencyconversionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(CommissionId.class)
public class Commission {
    @Id
    private String currencyFrom;
    @Id
    private String currencyTo;
    private Double commission;
}
