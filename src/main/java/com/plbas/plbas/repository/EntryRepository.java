package com.plbas.plbas.repository;

import com.plbas.plbas.entity.Entry;
import com.plbas.plbas.entity.Transaction;
import com.plbas.plbas.enums.CategoryDirection;
import com.plbas.plbas.enums.EntryDirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry,Long> {

    List<Entry> findByTransaction(Transaction transaction);

    List<Entry> findByTransactionIn(List<Transaction> transactions);

    List<Entry> findByDirectionAndCategoryDirection(EntryDirection direction, CategoryDirection categoryDirection);
}
