package com.alexnail.currencyconversionservice.service.impl;

import com.alexnail.currencyconversionservice.model.Wallet;
import com.alexnail.currencyconversionservice.repository.WalletRepository;
import com.alexnail.currencyconversionservice.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;

    public List<Wallet> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Wallet getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Wallet create(Wallet wallet) {
        return repository.save(wallet);
    }

    public void update(Wallet wallet) {
        if (repository.existsById(wallet.getId()))
            repository.save(wallet);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void setValue(Long walletId, BigDecimal value) {
        Wallet wallet = repository.findById(walletId).orElseThrow();
        wallet.setAmount(value);
        repository.save(wallet);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void withdraw(Long walletId, BigDecimal amount) {
        Wallet wallet = getById(walletId);
        BigDecimal value = wallet.getAmount();
        wallet.setAmount(value.subtract(amount));
        repository.save(wallet);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deposit(Long walletId, BigDecimal amount) {
        Wallet wallet = getById(walletId);
        BigDecimal value = wallet.getAmount();
        wallet.setAmount(value.add(amount));
        repository.save(wallet);
    }
}
