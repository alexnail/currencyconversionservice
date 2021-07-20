package com.alexnail.currencyconversionservice.repository;

import com.alexnail.currencyconversionservice.model.Commission;
import com.alexnail.currencyconversionservice.model.CommissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, CommissionId> {

}
