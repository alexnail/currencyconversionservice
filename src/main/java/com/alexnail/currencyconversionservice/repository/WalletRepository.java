package com.alexnail.currencyconversionservice.repository;

import com.alexnail.currencyconversionservice.model.Wallet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository {
    List<Wallet> getAll();

    Wallet findById(Long id);

    Long save(Wallet wallet);

    void update(Wallet wallet);

    void delete(Long id);
}
