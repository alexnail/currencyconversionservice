package com.alexnail.currencyconversionservice.repository;

import com.alexnail.currencyconversionservice.model.ExchangeRate;
import com.alexnail.currencyconversionservice.model.ExchangeRateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, ExchangeRateId> {
    @Query("select er from ExchangeRate er" +
            " where er.fromCurrency = :from and er.toCurrency = :to and er.timestamp =" +
            " (select max(er.timestamp) from ExchangeRate er where er.fromCurrency = :from and er.toCurrency = :to)")
    ExchangeRate findByLatestTimestamp(@Param("from")String fromCurrency, @Param("to")String toCurrency);
}