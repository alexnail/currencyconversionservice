package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.model.Wallet;
import com.alexnail.currencyconversionservice.repository.WalletRepository;
import com.alexnail.currencyconversionservice.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;

    public List<Wallet> findAll() {
        return repository.getAll();
    }

    public Wallet get(Long id) {
        return repository.findById(id);
    }

    public Long create(Wallet wallet) {
        return repository.save(wallet);
    }

    public void update(Wallet wallet) {
        repository.update(wallet);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    @Transactional
    public void setValue(Long walletId, BigDecimal value) {
        Wallet wallet = repository.findById(walletId);
        wallet.setValue(value);
        repository.save(wallet);
    }
}