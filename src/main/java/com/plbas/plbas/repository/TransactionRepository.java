package com.plbas.plbas.repository;

import com.plbas.plbas.entity.Transaction;
import com.plbas.plbas.service.DTO.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Transaction getByTx_no(String tx_no);

    Page<Transaction> findAll(Pageable pageable);

    Transaction findByTx_no(String tx_no);
}
