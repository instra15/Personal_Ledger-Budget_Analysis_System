package com.plbas.plbas.repository;

import com.plbas.plbas.entity.Entry;
import com.plbas.plbas.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry,Long> {

    Entry findByTransaction(Transaction transaction);

    List<Entry> findAllByTransaction(Transaction transaction);
}
