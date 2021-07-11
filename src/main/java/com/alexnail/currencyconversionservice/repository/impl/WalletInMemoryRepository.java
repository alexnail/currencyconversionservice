package com.alexnail.currencyconversionservice.repository.impl;

import com.alexnail.currencyconversionservice.model.Wallet;
import com.alexnail.currencyconversionservice.repository.WalletRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class WalletInMemoryRepository implements WalletRepository {
    private Map<Long, Wallet> wallets = new HashMap<>();

    @PostConstruct
    private void init() {
        wallets.put(1L, Wallet.builder()
                .id(1L)
                .value(BigDecimal.valueOf(100))
                .currency("EUR").build());
    }

    @Override
    public List<Wallet> getAll() {
        return new ArrayList<>(wallets.values());
    }

    @Override
    public Wallet findById(Long id) {
        return wallets.get(id);
    }

    @Override
    public Long create(Wallet wallet) {
        long newId = wallets.keySet().stream().max(Long::compare).get();
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
