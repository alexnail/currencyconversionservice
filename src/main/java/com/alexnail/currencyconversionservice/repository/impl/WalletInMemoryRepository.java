package com.alexnail.currencyconversionservice.repository.impl;

import com.alexnail.currencyconversionservice.model.Wallet;
import com.alexnail.currencyconversionservice.repository.WalletRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class WalletInMemoryRepository implements WalletRepository {

    private final Map<Long, Wallet> wallets = new HashMap<>();

    @Override
    public Iterable<Wallet> findAll() {
        return new ArrayList<>(wallets.values());
    }

    @Override
    public Wallet save(Wallet wallet) {
        long newId = wallets.keySet().stream().max(Long::compare).orElse(0L);
        wallet.setId(++newId);
        wallets.put(newId, wallet);
        return wallet;
    }

    @Override
    public void deleteById(Long id) {
        wallets.remove(id);
    }

    @Override
    public <S extends Wallet> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return Optional.ofNullable(wallets.get(id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return wallets.get(aLong) != null;
    }

    @Override
    public Iterable<Wallet> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return wallets.size();
    }

    @Override
    public void delete(Wallet entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Wallet> entities) {

    }

    @Override
    public void deleteAll() {
        wallets.clear();
    }
}
