package com.alexnail.currencyconversionservice.repository;

import com.alexnail.currencyconversionservice.model.Commission;
import com.alexnail.currencyconversionservice.model.CommissionId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CommissionInMemoryRepository implements CommissionRepository {

    private final Map<CommissionId, Commission> commissions = new HashMap<>();

    @Override
    public <S extends Commission> S save(S entity) {
        CommissionId commissionId = new CommissionId();
        commissionId.setCurrencyFrom(entity.getCurrencyFrom());
        commissionId.setCurrencyTo(entity.getCurrencyTo());
        commissions.put(commissionId, entity);
        return entity;
    }

    @Override
    public List<Commission> findAll() {
        return new ArrayList<>(commissions.values());
    }

    @Override
    public List<Commission> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Commission> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Commission> findAllById(Iterable<CommissionId> iterable) {
        return null;
    }

    @Override
    public long count() {
        return commissions.size();
    }

    @Override
    public void deleteById(CommissionId commissionId) {
        commissions.remove(commissionId);
    }

    @Override
    public void delete(Commission entity) {
        CommissionId commissionId = new CommissionId();
        commissionId.setCurrencyFrom(entity.getCurrencyFrom());
        commissionId.setCurrencyTo(entity.getCurrencyTo());
        deleteById(commissionId);
    }

    @Override
    public void deleteAllById(Iterable<? extends CommissionId> commissionIds) {

    }

    @Override
    public void deleteAll(Iterable<? extends Commission> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Commission> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Commission> findById(CommissionId commissionId) {
        return Optional.ofNullable(commissions.get(commissionId));
    }

    @Override
    public boolean existsById(CommissionId commissionId) {
        return commissions.containsKey(commissionId);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Commission> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public <S extends Commission> List<S> saveAllAndFlush(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Commission> iterable) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<CommissionId> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Commission getOne(CommissionId commissionId) {
        return commissions.get(commissionId);
    }

    @Override
    public Commission getById(CommissionId commissionId) {
        return commissions.get(commissionId);
    }

    @Override
    public <S extends Commission> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Commission> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Commission> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Commission> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Commission> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Commission> boolean exists(Example<S> example) {
        return false;
    }
}
