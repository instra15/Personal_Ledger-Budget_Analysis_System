package com.plbas.plbas.repository;

import com.plbas.plbas.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Transaction getByTx_no(String tx_no);

}
