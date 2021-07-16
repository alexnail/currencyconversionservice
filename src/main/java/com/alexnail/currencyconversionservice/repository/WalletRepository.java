package com.alexnail.currencyconversionservice.repository;

import com.alexnail.currencyconversionservice.model.Wallet;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface WalletRepository extends CrudRepository<Wallet, Long> {
}
