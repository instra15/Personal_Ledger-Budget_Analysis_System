package com.plbas.plbas.repository;

import com.plbas.plbas.entity.Transaction;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Transaction findByTxNo(String tx_no);

    List<Transaction> findAllByDateBetween(Date start, Date end);

    boolean existsByTxNo(@NotBlank String txNo);
}
