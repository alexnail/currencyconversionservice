package com.alexnail.currencyconversionservice.service;

import com.alexnail.currencyconversionservice.model.Wallet;

import java.util.List;

public interface WalletService {

    List<Wallet> findAll();

    Wallet get(Long id);

    Long create(Wallet wallet);

    void update(Wallet wallet);

    void delete(Long id);
}
