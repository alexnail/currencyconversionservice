package com.alexnail.currencyconversionservice.service;

import com.alexnail.currencyconversionservice.model.Wallet;
import com.alexnail.currencyconversionservice.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WalletService {

    private WalletRepository repository;

    public List<Wallet> findAll() {
        return repository.getAll();
    }

    public Wallet findById(Long id) {
        return repository.findById(id);
    }

    public Long create(Wallet wallet) {
        return repository.create(wallet);
    }

    public void update(Wallet wallet) {
        repository.update(wallet);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
