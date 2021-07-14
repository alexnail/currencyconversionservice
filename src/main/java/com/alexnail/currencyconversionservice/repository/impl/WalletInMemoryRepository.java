package com.alexnail.currencyconversionservice.repository.impl;

import com.alexnail.currencyconversionservice.model.Wallet;
import com.alexnail.currencyconversionservice.repository.WalletRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WalletInMemoryRepository implements WalletRepository {

    private final Map<Long, Wallet> wallets = new HashMap<>();

    @Override
    public List<Wallet> getAll() {
        return new ArrayList<>(wallets.values());
    }

    @Override
    public Wallet findById(Long id) {
        return wallets.get(id);
    }

    @Override
    public Long save(Wallet wallet) {
        long newId = wallets.keySet().stream().max(Long::compare).orElse(0L);
        wallet.setId(++newId);
        wallets.put(newId, wallet);
        return newId;
    }

    @Override
    public void update(Wallet wallet) {
        wallets.replace(wallet.getId(), wallet);
    }

    @Override
    public void delete(Long id) {
        wallets.remove(id);
    }
}
