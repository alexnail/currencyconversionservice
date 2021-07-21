package com.alexnail.currencyconversionservice.repository;

import com.alexnail.currencyconversionservice.model.ExchangeRate;
import com.alexnail.currencyconversionservice.model.ExchangeRateId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExchangeRateInMemoryRepository implements ExchangeRateRepository {

    private static final Map<Pair<String, String>, ExchangeRate> rates = new HashMap<>();

    @Override
    public ExchangeRate findByLatestTimestamp(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency))
            return new ExchangeRate(fromCurrency, toCurrency, BigDecimal.ONE, Timestamp.from(Instant.now()));
        return rates.get(Pair.of(fromCurrency, toCurrency));
    }


    @Override
    public List<ExchangeRate> findAll() {
        return null;
    }

    @Override
    public List<ExchangeRate> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ExchangeRate> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<ExchangeRate> findAllById(Iterable<ExchangeRateId> exchangeRateIds) {
        return null;
    }

    @Override
    public long count() {
        return rates.size();
    }

    @Override
    public void deleteById(ExchangeRateId exchangeRateId) {

    }

    @Override
    public void delete(ExchangeRate exchangeRate) {

    }

    @Override
    public void deleteAllById(Iterable<? extends ExchangeRateId> iterable) {

    }

    @Override
    public void deleteAll(Iterable<? extends ExchangeRate> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends ExchangeRate> S save(S s) {
        return (S) rates.put(Pair.of(s.getFromCurrency(), s.getToCurrency()), s);
    }

    @Override
    public <S extends ExchangeRate> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ExchangeRate> findById(ExchangeRateId exchangeRateId) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(ExchangeRateId exchangeRateId) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends ExchangeRate> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ExchangeRate> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ExchangeRate> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<ExchangeRateId> exchangeRateIds) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ExchangeRate getOne(ExchangeRateId exchangeRateId) {
        return null;
    }

    @Override
    public ExchangeRate getById(ExchangeRateId exchangeRateId) {
        return null;
    }

    @Override
    public <S extends ExchangeRate> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ExchangeRate> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ExchangeRate> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ExchangeRate> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ExchangeRate> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ExchangeRate> boolean exists(Example<S> example) {
        return false;
    }
}
